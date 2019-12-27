package com.bkw.mymvp.mvp


import android.util.Log

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 管理RxJava,配合MVP模式，页面销毁时取消订阅
 *
 * @author bkw
 * @date 2018/4/18
 */

class RxManager {

    private val mCompositeDisposable = CompositeDisposable()

    fun register(d: Disposable) {
        Log.d("RxManager", "mCompositeDisposable register")
        mCompositeDisposable.add(d)
    }

    fun unSubscribe() {
        Log.d("RxManager", "mCompositeDisposable unSubscribe")
        mCompositeDisposable.dispose()
    }
}
