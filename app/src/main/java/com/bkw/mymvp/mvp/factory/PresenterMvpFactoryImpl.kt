package com.bkw.mymvp.mvp.factory


import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter
import com.bkw.mymvp.mvp.view.BaseMvpView

/**
 * @description Presenter工厂实现类
 */
class PresenterMvpFactoryImpl<V : BaseMvpView, P : BaseMvpPresenter<V>> private constructor(
        /**
         * 需要创建的Presenter的类型
         */
        private val mPresenterClass: Class<P>) : PresenterMvpFactory<V, P> {

    override fun createMvpPresenter(): P {
        try {
            return mPresenterClass.newInstance()
        } catch (e: Exception) {
            throw RuntimeException("Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解")
        }

    }

    companion object {

        /**
         * 根据注解创建Presenter的工厂实现类
         *
         * @param viewClazz 需要创建Presenter的V层实现类
         * @param <V>       当前View实现的接口类型
         * @param <P>       当前要创建的Presenter类型
         * @return 工厂类
        </P></V> */
        fun <V : BaseMvpView, P : BaseMvpPresenter<V>> createFactory(viewClazz: Class<*>): PresenterMvpFactoryImpl<V, P>? {
            val annotation = viewClazz.getAnnotation(CreatePresenter::class.java)
            var aClass: Class<P>? = null
            if (annotation != null) {
                aClass = annotation.value as Class<P>
            }
            return if (aClass == null) null else PresenterMvpFactoryImpl(aClass)
        }
    }
}
