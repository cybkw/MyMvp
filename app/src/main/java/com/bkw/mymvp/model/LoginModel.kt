package com.bkw.mymvp.model

import com.bkw.mymvp.entity.BaseResponse
import com.bkw.mymvp.mvp.RxHelper
import com.bkw.mymvp.net.BaseApi
import com.bkw.mymvp.net.RetrofitCreateHelper
import io.reactivex.Observable
import okhttp3.RequestBody

open class LoginModel private constructor() {
    companion object {

        fun newInstance(): LoginModel {
            return LoginModel()
        }
    }

    /**
     * 登录
     */
    fun login(body: RequestBody): Observable<BaseResponse<*>> {
        return RetrofitCreateHelper.createApi(BaseApi::class.java, BaseApi.HOST)
                .login(body)
                .compose(RxHelper.rxSchedulerHelper())
    }


}