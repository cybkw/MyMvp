package com.bkw.mymvp.base

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseFragment : Fragment() {

    internal var binder: Unbinder? = null


    protected abstract val layoutId: Int

    val layoutView: View?
        get() = null

    /**
     * 是否显示状态布局
     *
     * @return
     */
    protected val isShowStatusView: Boolean
        get() = false


    protected var mToast: Toast? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutView != null) {
            layoutView
        } else inflater.inflate(layoutId, container, false)


    }

    /**
     * 状态布局： 重试按钮点击事件
     */
    protected fun onRetryClicked() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder = ButterKnife.bind(this, view)
        getBundle(arguments)
        initUI(view)
        initData()
    }


    /**
     * 初始化UI
     */
    abstract fun initUI(view: View)

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 获得Activity传递的值
     */
    private fun getBundle(bundle: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binder != null) {
            binder!!.unbind()
        }
    }

    /**
     * 隐藏键盘
     *
     * @param editText 说明:在华为CLT ALOO 系统版本Android 9,level 28发生了view.getWindowToken异常,
     * 原因是找不到所依附的View,所以建议传递一个当前页面的View
     * @return 隐藏键盘结果
     *
     *
     * true:隐藏成功
     *
     *
     * false:隐藏失败
     */
    protected fun hiddenKeyboard(editText: View): Boolean {
        //点击空白位置 隐藏软键盘
        val mInputMethodManager = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputMethodManager != null && mInputMethodManager.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    /**
     * 提示窗口
     */
    fun showHint(context: Context, msg: String) {
        AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("知道了") { dialog, which -> }.show()
    }

    fun startActivity(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
    }

    fun startAc(intent: Intent) {
        startActivity(intent)
        defaultAnimation()
    }

    /**
     * 默认转场动画
     */
    private fun defaultAnimation() {}

    fun showToast(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = Toast.LENGTH_LONG
        }
        mToast!!.show()
    }

    fun showToastCenter(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(activity, text, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(text)
        }
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

}
