package com.omarkrostom.bilal.di

import android.content.Context
import com.omarkrostom.bilal.BilalApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val bilalApplication: BilalApplication) {

    @Provides
    fun providesAppContext(bilalApplication: BilalApplication): Context = bilalApplication

}