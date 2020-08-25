package com.omarkrostom.bilal

import com.omarkrostom.bilal.base.BaseActivity
import com.omarkrostom.bilal.managers.LocationManager
import javax.inject.Inject

class TempActivity: BaseActivity(R.layout.activity_temp) {

    @Inject
    lateinit var locationManager: LocationManager

}