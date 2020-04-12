package com.omarkrostom.bilal

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BilalApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? = null
}