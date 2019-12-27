package com.bkw.mymvp.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import butterknife.ButterKnife

/**
 * 基类Activity
 *
 * @author bkw
 */
abstract class BaseActivity : Activity() {

    /**
     * 全局上下文对象
     */
    protected  var mContext: Context? = null

    /**
     * 是否显示状态布局
     *
     * @return
     */
    protected val isShowStatusView: Boolean
        get() = false

    protected abstract val layoutId: Int

    protected var mToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        transparentStatusBar()
        super.onCreate(savedInstanceState)
        mContext = this
        init(savedInstanceState)
    }

    protected fun init(savedInstanceState: Bundle?) {
        val inflateView = layoutInflater.inflate(layoutId, null, false)

        setContentView(inflateView)


        ButterKnife.bind(this)
        initView(savedInstanceState)
        setupData()
    }

    /**
     * 状态布局： 重试按钮点击事件
     */
    protected fun onRetryClicked() {

    }

    /**
     * 检测系统版本并使状态栏全透明
     */
    protected fun transparentStatusBar() {
        //        StatusBarUtils.setColor(this, R.color.transparent, true);
    }

    /**
     * 初始化View
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    abstract fun setupData()

    override fun isActivityTransitionRunning(): Boolean {
        return true
    }

    /**
     * 提示窗口
     */
    fun showHint(context: Context, msg: String) {
        AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("确认") { dialog, which -> }.show()
    }

    fun showToast(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = Toast.LENGTH_LONG
        }
        mToast!!.show()
    }

    fun showToastCenter(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(text)
        }
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

    private fun fadeAnimation() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * 默认转场动画
     */
    private fun defaultAnimation() {}


    protected fun startAc(clz: Class<*>) {
        startActivity(Intent(this, clz))
        defaultAnimation()
    }

    protected fun startAc(clz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clz), requestCode)
        defaultAnimation()
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    fun startAc(clz: Class<*>, intent: Intent) {
        intent.setClass(this, clz)
        startActivity(intent)
        defaultAnimation()
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    fun startAc(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        defaultAnimation()
    }

    fun startAc(intent: Intent) {
        startActivity(intent)
        defaultAnimation()
    }

    protected fun startAc(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
        defaultAnimation()
    }

    fun startAcBottom(intent: Intent) {
        startActivity(intent)
    }

    fun startAcBottom(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    /**
     * finish当前activity 移动动画
     */
    protected fun finishAc() {
        finish()
    }

    protected fun finishAcAlpha() {
        finish()
    }

    protected fun finishAcBottom() {
        finish()
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
        val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputMethodManager != null && mInputMethodManager.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}
