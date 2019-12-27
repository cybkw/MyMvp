package com.bkw.mymvp.mvp.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log

import com.bkw.mymvp.base.BaseActivity
import com.bkw.mymvp.mvp.factory.PresenterMvpFactory
import com.bkw.mymvp.mvp.factory.PresenterMvpFactoryImpl
import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.proxy.BaseMvpProxy
import com.bkw.mymvp.mvp.proxy.PresenterProxyInterface


/**
 * @author bkw
 * @description
 */
@SuppressLint("Registered")
abstract class AbstractMvpActivity<V : BaseMvpView, P : BaseMvpPresenter<V>> : BaseActivity(), PresenterProxyInterface<V, P> {


    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private val mProxy = BaseMvpProxy(PresenterMvpFactoryImpl.createFactory<V, P>(javaClass))

    override var presenterFactory: PresenterMvpFactory<V, P>
        get() {
            Log.e("perfect-mvp", "V getPresenterFactory")
            return mProxy.presenterFactory
        }
        set(presenterFactory) {
            Log.e("perfect-mvp", "V setPresenterFactory")
            mProxy.presenterFactory = presenterFactory
        }

    override//判断是否已绑定View,未绑定再次绑定
    val mvpPresenter: P
        get() {
            Log.e("perfect-mvp", "V getMvpPresenter")
            if (mProxy.mvpPresenter.mvpView == null) {
                mProxy.mvpPresenter.onAttachMvpView(this as V)
            }
            return mProxy.mvpPresenter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("perfect-mvp", "V onCreate")
        Log.e("perfect-mvp", "V onCreate mProxy = $mProxy")
        Log.e("perfect-mvp", "V onCreate this = " + this.hashCode())
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY)!!)
        }

    }

    override fun onResume() {
        super.onResume()
        //绑定View层
        mProxy.onResume(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.e("perfect-mvp", "V onDestroy = $isChangingConfigurations")
        }
        Log.e("perfect-mvp", "V onDestroy = ")
        mProxy.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("perfect-mvp", "V onSaveInstanceState")
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState())
    }

    companion object {
        private val PRESENTER_SAVE_KEY = "presenter_save_key"
    }
}
