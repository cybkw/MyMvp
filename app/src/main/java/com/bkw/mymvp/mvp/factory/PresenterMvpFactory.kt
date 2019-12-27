package com.bkw.mymvp.mvp.factory


import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.view.BaseMvpView

/**
 * @description Presenter工厂接口
 */
interface PresenterMvpFactory<V : BaseMvpView, P : BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    fun createMvpPresenter(): P
}
