package com.routinew.espresso.di

import android.content.Context
import com.routinew.espresso.R
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviders {
/*
    @Singleton
    @Provides
    fun provideEspressoInterface(@ApplicationContext context: Context): EspressoInterface {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.ESPRESSO_DEV_SERVER))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(EspressoInterface::class.java)
    }
    */
/*
    @Singleton
    @Provides
    fun provideRestaurantRepository(espressoInterface: EspressoInterface)
            = RestaurantRepository(espressoInterface)

 */
}