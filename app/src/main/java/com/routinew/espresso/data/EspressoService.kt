
package com.routinew.espresso.data

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.preference.PreferenceManager
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
import retrofit2.http.Header
import timber.log.Timber

import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Predicate

interface EspressoInterface {
    @GET("restaurants")
    fun getRestaurantList(
//        @Header("Authorization") authorization : String?
    ) : Call<EspressoRestaurantListPacket>

    @GET("restaurants/{id}")
    fun getRestaurant(
//        @Header("Authorization") authorization: String?,
        @Path("id") restaurantId: Int
    ): Call<EspressoRestaurantPacket>

//    @POST("restaurants/create")
//    fun createRestaurant = TODO()

//    @PUT("restaurants/{id}")
//    fun updateRestaurant(@Path("id") restaurantId: Int)
}

class EspressoService {

    companion object {
        @Volatile
        private lateinit var INSTANCE: EspressoInterface
        private val initialized = AtomicBoolean(false)

        // these will change and that should trigger the interface to rebuild.
        private lateinit var apiPath: String
        private lateinit var server: String
        private lateinit var defaultServer: String

        private lateinit var context: Context

        val instance: EspressoInterface get() = INSTANCE

        /**
         * can set credentials to null.
         */
        var credentials: Credentials? = null

        val credentialInterceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val accessToken = credentials?.accessToken
            // this should handle authorization only when we have an access token
            if (null != accessToken) {
                builder.header("Authorization", "Bearer $accessToken")
            }
            chain.proceed(builder.build())
        }

        fun updateServerLoc(newApiPath: String, newServer: String) {
            // call on prefs changed
            if (newApiPath != apiPath || newServer != server) {
                apiPath = newApiPath
                server = newServer
            }
            createService(apiPath, server)
        }

        fun buildInterface(context: Context) {
            if (!initialized.getAndSet(true)) {
                // these won't change
                apiPath = context.getString(R.string.ESPRESSO_API_BASE)
                defaultServer = context.getString(R.string.ESPRESSO_DEV_SERVER)

                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                server = if (sharedPreferences.getBoolean("usecustomerserverlocation", false)) ({
                    sharedPreferences.getString("customserverlocation", defaultServer)
                        .also { Timber.i("Server Location $it") }
                }).toString() else {
                    defaultServer
                }

                sharedPreferences.registerOnSharedPreferenceChangeListener(setupListener)
                createService(apiPath, server)

                this.context = context
            }
        }

        private fun createService(apiPath: String, server: String) {
            INSTANCE = ServiceGenerator.createService(
                EspressoInterface::class.java,
                server, apiPath, credentialInterceptor
            )
        }

        private val setupListener = object : SharedPreferences.OnSharedPreferenceChangeListener {
            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences?,
                key: String?
            ) {
                sharedPreferences ?: return
                val debugString = "Shared Prefs: Key: $key"
                Timber.i(debugString)

                var isChanged = false
                with(sharedPreferences) {
                    val isDefault = getBoolean("usecustomserverlocation", false)
                    val newServer = getString("customserverlocation", defaultServer)!!

                    when (key) {
                        "usecustomerserverlocation" -> {
                            when {
                                isDefault && server != defaultServer -> {
                                    isChanged = true
                                    server = defaultServer
                                }
                                !isDefault && server != newServer -> {
                                    isChanged = true
                                    server = newServer
                                }
                            }
                        }
                        "customserverlocation" -> if (server != newServer) {
                            isChanged = true
                            server = newServer
                        }
                    }
                }

                if (isChanged) {
                    createService(server, apiPath)
                }
            } // method
        } // prefs listener
    } // companion object

} // class
