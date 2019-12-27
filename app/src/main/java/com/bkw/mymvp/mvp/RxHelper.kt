package com.bkw.mymvp.mvp


import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 事件总线-辅助类
 *
 * @date 2017/9/12
 *
 *
 */
object RxHelper {
    /**
     * 统一线程处理
     *
     *
     * 发布事件io线程，接收事件主线程
     */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {//compose处理线程
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 生成Flowable
     *
     * @param t
     * @return Flowable
     */
    fun <T> createFlowable(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * 生成Observable
     *
     * @param t
     * @return Flowable
     */
    fun <T> createObservable(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}
