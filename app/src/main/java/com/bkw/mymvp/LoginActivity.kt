package com.bkw.mymvp

import android.os.Bundle

import com.bkw.mymvp.mvp.factory.CreatePresenter
import com.bkw.mymvp.mvp.view.AbstractMvpActivity
import com.bkw.mymvp.presenter.LoginPresenter
import com.bkw.mymvp.views.ILoginView

@CreatePresenter(LoginPresenter::class)
class LoginActivity : AbstractMvpActivity<ILoginView, LoginPresenter>(), ILoginView {
    override fun sendCodeSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取验证码
        mvpPresenter.login("bkw")

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onFailed(msg: String) {

    }

    override fun onError(msg: String) {

    }
}
