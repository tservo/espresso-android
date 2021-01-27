package com.routinew.espresso

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.LoginService
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber

@HiltAndroidApp
class EspressoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // these should stay up forever
        EspressoService.buildInterface(this)
        LoginService.initialize(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            // add the stetho diagnostic tools
            Stetho.initializeWithDefaults(this)
        }
    }
}