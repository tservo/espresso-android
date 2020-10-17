package com.routinew.espresso.utilities

import com.routinew.espresso.data.EspressoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object EspressoModule {

    @Provides
    fun provideAnalyticsService(): EspressoService {
        return Retrofit.Builder()
                .baseUrl("http://127.0.0.1:5000/") // TODO: place in config file
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(EspressoService::class.java)
    }
}