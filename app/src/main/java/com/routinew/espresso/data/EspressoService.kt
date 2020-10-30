package com.routinew.espresso.data

import android.content.Context
import com.routinew.espresso.R
import com.routinew.espresso.data.json.EspressoRestaurantListPacket
import com.routinew.espresso.data.json.EspressoRestaurantPacket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import java.util.concurrent.atomic.AtomicBoolean

interface EspressoInterface {
    @GET("restaurants")
    fun getRestaurantList() : Call<EspressoRestaurantListPacket>

    @GET("restaurant/{id}")
    fun getRestaurant(@Path("id") restaurantId: Int): Call<EspressoRestaurantPacket>

//    @POST("restaurants/create")
//    fun createRestaurant = TODO()

//    @PUT("restaurants/{id}")
//    fun updateRestaurant(@Path("id") restaurantId: Int)
}

class EspressoService {

    companion object {
        @Volatile private lateinit var INSTANCE: EspressoInterface
        private val initialized = AtomicBoolean(false)

        val instance: EspressoInterface get() = INSTANCE

        fun buildInterface(context: Context) {
            if (!initialized.getAndSet(true)) {
                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

                INSTANCE = Retrofit.Builder()
                    .baseUrl(context.getString(R.string.ESPRESSO_DEV_SERVER))
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(EspressoInterface::class.java)
            }
        }
    }

}