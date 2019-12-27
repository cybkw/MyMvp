package com.bkw.mymvp.mvp.view

/**
 * @author bkw
 * @description 所有View层接口的基类
 */
interface BaseMvpView {

    /**
     * 显示全屏默认loading对话框
     */
    fun showProgress()

    /**
     * 关闭全屏默认loading对话框
     */
    fun hideProgress()

    /**
     * 显示警告级提示信息
     *
     * @param msg
     */
    fun onFailed(msg: String)

    /**
     * 显示错误提示信息,此方法用于显示级别较高的错误，如非服务器发送的提示信息
     */
    fun onError(msg: String)

}
