package com.omarkrostom.bilal.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun providesFusedLocationProviderClient(ctx: Context) =
        LocationServices.getFusedLocationProviderClient(ctx)

}