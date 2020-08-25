package com.omarkrostom.bilal.base

import android.annotation.SuppressLint
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered")
open class BaseActivity(private val layout: Int): DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

}