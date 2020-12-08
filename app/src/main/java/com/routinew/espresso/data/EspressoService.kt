package com.routinew.espresso.data

import android.content.Context
import com.auth0.android.result.Credentials
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.routinew.espresso.BuildConfig
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.util.concurrent.atomic.AtomicBoolean

interface EspressoInterface {
    @GET("restaurants")
    fun getRestaurantList() : Call<EspressoRestaurantListPacket>

    @GET("restaurants/{id}")
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

        private var credentials: Credentials? = null

        /**
         * can set credentials to null.
         */
        fun setCredentials(credentials: Credentials?) {
            this.credentials = credentials
        }

        fun buildInterface(context: Context) {
            if (!initialized.getAndSet(true)) {
                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

                val clientBuilder = OkHttpClient.Builder().apply {
                    addInterceptor { chain ->
                        val accessToken = credentials?.accessToken
                        val builder = chain.request().newBuilder()
                        // this should handle authorization only when we have an access token
                        if (null != accessToken) {
                            builder.header("Authorization","Bearer $accessToken")
                        }
                        chain.proceed(builder.build())
                    }

                    if (BuildConfig.DEBUG) {
                        addNetworkInterceptor(StethoInterceptor())

                        val httpLoggingInterceptor = HttpLoggingInterceptor()
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                        addNetworkInterceptor(httpLoggingInterceptor)
                    }
                }


                INSTANCE = Retrofit.Builder()
                    .baseUrl(context.getString(R.string.ESPRESSO_DEV_SERVER) +
                            context.getString(R.string.ESPRESSO_API_BASE))
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(clientBuilder.build())
                    .build()
                    .create(EspressoInterface::class.java)
            }
        }
    }

}