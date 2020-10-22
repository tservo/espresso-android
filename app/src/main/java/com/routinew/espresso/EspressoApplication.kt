package com.routinew.espresso

import android.app.Application
import com.routinew.espresso.data.EspressoService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EspressoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        EspressoService.buildInterface(applicationContext)
    }
}