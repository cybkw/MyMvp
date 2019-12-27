package com.bkw.mymvp.mvp.proxy


import com.bkw.mymvp.mvp.factory.PresenterMvpFactory
import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.view.BaseMvpView

/**
 * @description 代理接口
 */
interface PresenterProxyInterface<V : BaseMvpView, P : BaseMvpPresenter<V>> {

    /**
     * 获取Presenter的工厂类
     * @return 返回PresenterMvpFactory类型
     */
    /**
     * 设置创建Presenter的工厂
     * @param presenterFactory PresenterFactory类型
     */
    var presenterFactory: PresenterMvpFactory<V, P>


    /**
     * 获取创建的Presenter
     * @return 指定类型的Presenter
     */
    val mvpPresenter: P


}
