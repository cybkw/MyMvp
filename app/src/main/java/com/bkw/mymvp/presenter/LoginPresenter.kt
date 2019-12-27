package com.bkw.mymvp.presenter

import com.bkw.mymvp.model.LoginModel
import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.views.ILoginView
import okhttp3.MediaType
import okhttp3.RequestBody

class LoginPresenter : BaseMvpPresenter<ILoginView>() {

    fun login(username: String) {

        val body = RequestBody.create(MediaType.parse("utf-8"), username)
        mRxManager.register(LoginModel.newInstance()
                .login(body)
                .subscribe({
                    if (200 === it.code) {
                        mvpView?.sendCodeSuccess()
                    } else {
                        mvpView?.onFailed("s")
                    }
                }, { throwable -> mvpView?.onError(throwable.toString()) }))
    }
}
