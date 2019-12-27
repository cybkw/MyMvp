package com.bkw.mymvp.mvp.proxy

import android.os.Bundle
import android.util.Log
import com.bkw.mymvp.mvp.factory.PresenterMvpFactory
import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.view.BaseMvpView



/**
 * @author bkw
 * @description 代理实现类，用来管理Presenter的生命周期，还有和view之间的关联
 */
class BaseMvpProxy<V : BaseMvpView, P : BaseMvpPresenter<V>>(
        /**
         * Presenter工厂类
         */
        private var mFactory: PresenterMvpFactory<V, P>?) : PresenterProxyInterface<V, P> {
    private var mPresenter: P? = null
    private var mBundle: Bundle? = null
    private var mIsAttchView: Boolean = false


    /**
     * 获取Presenter的工厂类
     *
     * @return PresenterMvpFactory类型
     */
    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getMvpPresenter()之前，如果Presenter已经创建则不能再修改
     *
     * @param presenterFactory PresenterFactory类型
     */

    override var presenterFactory: PresenterMvpFactory<V, P>

        get() = this!!.mFactory!!
        set(presenterFactory) {
            if (mPresenter != null) {
                throw IllegalArgumentException("这个方法只能在getMvpPresenter()之前调用，如果Presenter已经创建则不能再修改")
            }
            this.mFactory = presenterFactory
        }

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     * 如果之前创建过，而且是以外销毁则从Bundle中恢复
     */
    override val mvpPresenter: P
        get() {
            Log.e("perfect-mvp", "Proxy getMvpPresenter")
            if (mFactory != null) {
                if (mPresenter == null) {
                    mPresenter = mFactory!!.createMvpPresenter()
                    mPresenter!!.onCreatePersenter(if (mBundle == null) null else mBundle!!.getBundle(PRESENTER_KEY))
                }
            }

            if (mPresenter == null) {
                throw RuntimeException("Presenter is NULL, 请检查是否加入@CreatePresenter")
            }
            Log.e("perfect-mvp", "Proxy getMvpPresenter = " + mPresenter!!)
            return mPresenter as P
        }

    /**
     * 绑定Presenter和view
     *
     * @param mvpView
     */
    fun onResume(mvpView: V) {
        Log.e("perfect-mvp", "Proxy onResume")
        if (mPresenter != null && !mIsAttchView) {
            mPresenter!!.onAttachMvpView(mvpView)
            mIsAttchView = true
        }
    }

    /**
     * 销毁Presenter持有的View
     */
    private fun onDetachMvpView() {
        Log.e("perfect-mvp", "Proxy onDetachMvpView = ")
        if (mPresenter != null && mIsAttchView) {
            mPresenter!!.onDetachMvpView()
            mIsAttchView = false
        }
    }

    /**
     * 销毁Presenter
     */
    fun onDestroy() {
        Log.e("perfect-mvp", "Proxy onDestroy = ")
        if (mPresenter != null) {
            onDetachMvpView()
            mPresenter!!.onDestroyPersenter()
            mPresenter = null
        }
    }

    /**
     * 意外销毁的时候调用
     *
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    fun onSaveInstanceState(): Bundle {
        Log.e("perfect-mvp", "Proxy onSaveInstanceState = ")
        val bundle = Bundle()
        mvpPresenter
        if (mPresenter != null) {
            val presenterBundle = Bundle()
            //回调Presenter
            mPresenter!!.onSaveInstanceState(presenterBundle)
            bundle.putBundle(PRESENTER_KEY, presenterBundle)
        }
        return bundle
    }

    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState = ")
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState Presenter = " + mPresenter!!)
        mBundle = savedInstanceState

    }

    companion object {

        /**
         * 获取onSaveInstanceState中bundle的key
         */
        private val PRESENTER_KEY = "presenter_key"
    }
}
