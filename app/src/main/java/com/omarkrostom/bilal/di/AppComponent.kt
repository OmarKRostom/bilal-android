package com.omarkrostom.bilal.di

import com.omarkrostom.bilal.BilalApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ScreenInjectorModule::class,
    LocationModule::class,
    AppModule::class
])
interface AppComponent: AndroidInjector<BilalApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(bilalApplication: BilalApplication): Builder

        fun build(): AppComponent
    }

}