package com.bkw.mymvp.mvp.view

import android.os.Bundle
import android.view.View

import com.bkw.mymvp.base.BaseFragment
import com.bkw.mymvp.mvp.factory.PresenterMvpFactory
import com.bkw.mymvp.mvp.factory.PresenterMvpFactoryImpl
import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.proxy.BaseMvpProxy
import com.bkw.mymvp.mvp.proxy.PresenterProxyInterface


/**
 * @description 继承Fragment的MvpFragment基类
 */
class AbstractFragment<V : BaseMvpView, P : BaseMvpPresenter<V>> : BaseFragment(), PresenterProxyInterface<V, P> {
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private val mProxy = BaseMvpProxy(PresenterMvpFactoryImpl.createFactory<V, P>(javaClass))

    val layoutId: Int
        get() = 0

    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    override var presenterFactory: PresenterMvpFactory<V, P>
        get() = mProxy.presenterFactory
        set(presenterFactory) {
            mProxy.presenterFactory = presenterFactory
        }

    /**
     * 获取Presenter
     *
     * @return P
     */
    override//判断是否已绑定View,未绑定再次绑定
    val mvpPresenter: P
        get() {
            if (mProxy.mvpPresenter.mvpView == null) {
                mProxy.mvpPresenter.onAttachMvpView(this as V)
            }
            return mProxy.mvpPresenter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState)
        }

    }

    override fun onResume() {
        super.onResume()
        //绑定View层
        mProxy.onResume(this as V)
    }

    protected fun initData() {

    }

    fun initUI(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mProxy.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState())
    }

    companion object {

        /**
         * 调用onSaveInstanceState时存入Bundle的key
         */
        private val PRESENTER_SAVE_KEY = "presenter_save_key"
    }
}
