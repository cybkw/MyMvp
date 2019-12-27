package com.bkw.mymvp.entity

import java.io.Serializable

data class BaseResponse<T>(var code: Int = 0, var message: String?, var result: T) : Serializable {


    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\''.toString() +
                ", result=" + result +
                '}'.toString()
    }
}
