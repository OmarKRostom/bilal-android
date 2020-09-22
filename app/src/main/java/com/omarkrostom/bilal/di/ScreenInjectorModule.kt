package com.omarkrostom.bilal.di

import com.omarkrostom.bilal.TempActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreenInjectorModule {
    @ContributesAndroidInjector
    abstract fun contributeTempActivity(): TempActivity
}