package com.omarkrostom.bilal

import com.omarkrostom.bilal.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BilalApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? = DaggerAppComponent
        .builder()
        .application(this)
        .build()

}