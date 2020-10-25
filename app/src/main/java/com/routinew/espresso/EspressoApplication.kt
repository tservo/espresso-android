package com.routinew.espresso

import android.app.Application
import com.routinew.espresso.data.EspressoService
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber

@HiltAndroidApp
class EspressoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        EspressoService.buildInterface(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            // add the stetho diagnostic tools
//            Stetho.initializeWithDefaults(this)
//            OkHttpClient.Builder()
//                .addNetworkInterceptor(StethoInterceptor())
//                .build()
        }

    }
}