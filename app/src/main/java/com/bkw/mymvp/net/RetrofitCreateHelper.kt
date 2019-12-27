package com.bkw.mymvp.net


import com.bkw.mymvp.BuildConfig
import com.bkw.mymvp.net.converter.FastJsonConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 网络请求retrofit
 *
 * @author bkw
 */
object RetrofitCreateHelper {

    private val TIMEOUT_READ = 20
    private val TIMEOUT_CONNECTION = 20
    private val INTERCEPTOR = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val trustManager = object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

    private val sslSocketFactory: SSLSocketFactory?
        get() {
            var sslContext: SSLContext? = null
            var sslSocketFactory: SSLSocketFactory? = null
            try {
                sslContext = SSLContext.getInstance("SSL")
                sslContext!!.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
                sslSocketFactory = sslContext.socketFactory
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return sslSocketFactory
        }

    //缓存拦截器 使用第三方的工具库

    fun <T> createApi(clazz: Class<T>, url: String): T {
        //OkHttp
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(INTERCEPTOR)
        }
        okHttpClient.sslSocketFactory(sslSocketFactory!!, trustManager)
        okHttpClient.connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        okHttpClient.writeTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        okHttpClient.retryOnConnectionFailure(true)
        //        okHttpClient.addNetworkInterceptor(cacheInterceptor);
        //        okHttpClient.addInterceptor(cacheInterceptor);


        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }


}

