package com.bkw.mymvp.mvp.factory


import com.bkw.mymvp.mvp.presenter.BaseMvpPresenter

import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * @description 标注创建Presenter的注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
annotation class CreatePresenter(val value: KClass<out BaseMvpPresenter<*>>)
