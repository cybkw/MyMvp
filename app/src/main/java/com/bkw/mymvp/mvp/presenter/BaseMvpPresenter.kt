package com.bkw.mymvp.mvp.presenter

import android.os.Bundle
import android.util.Log
import com.alibaba.fastjson.JSON
import com.bkw.mymvp.mvp.RxManager
import com.bkw.mymvp.mvp.view.BaseMvpView
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


/**
 * @description 所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */
open class BaseMvpPresenter<V : BaseMvpView> {

    protected var mRxManager = RxManager()
    /**
     * V层view
     */
    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    var mvpView: V? = null
        private set

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    fun onCreatePersenter(savedState: Bundle?) {
        Log.e("perfect-mvp", "P onCreatePersenter = ")

    }


    /**
     * 绑定View
     */
    fun onAttachMvpView(mvpView: V) {
        this.mvpView = mvpView
        Log.e("perfect-mvp", "P onResume")
    }

    /**
     * 解除绑定View
     */
    fun onDetachMvpView() {
        mRxManager.unSubscribe()
        mvpView = null
        Log.e("perfect-mvp", "P onDetachMvpView = ")
    }

    /**
     * Presenter被销毁时调用
     */
    fun onDestroyPersenter() {
        Log.e("perfect-mvp", "P onDestroy = ")
    }

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    fun onSaveInstanceState(outState: Bundle) {
        Log.e("perfect-mvp", "P onSaveInstanceState = ")
    }

    companion object {

        fun request(`object`: JSONObject): RequestBody {
            return RequestBody.create(MediaType.parse("utf-8"), `object`.toString())
        }

        fun request(`object`: Any): RequestBody {
            return RequestBody.create(MediaType.parse("UTF-8"), JSON.toJSONString(`object`))
        }
    }
}
