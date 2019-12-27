package com.bkw.mymvp.net


import com.bkw.mymvp.entity.BaseResponse

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 服务接口列表
 */
interface BaseApi {

    /**
     * 登录
     */
    @POST("login")
    fun login(@Body body: RequestBody): Observable<BaseResponse<*>>

    companion object {

        /**
         * 服务地址
         */
        //线上192.168.1.222:8081- 本地：106:8083
        //    public static final String HOST = "http://192.168.1.106:8083/api/";
        val HOST = "http://140.207.34.219:8081/api/"
    }
}
