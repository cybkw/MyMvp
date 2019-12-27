package com.bkw.mymvp.net.converter

import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

/**
 * FastJson响应体转换器
 *
 * @param <T>
</T> */
class FastJsonResponseBodyConverter<T>(private val type: Type) : Converter<ResponseBody, T> {

    /**
     * 转换方法
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val bufferedSource = Okio.buffer(value.source())
        val str = bufferedSource.readUtf8()
        bufferedSource.close()
        return JSON.parseObject(str, type)
    }
}
