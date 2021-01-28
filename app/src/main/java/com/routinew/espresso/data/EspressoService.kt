
package com.routinew.espresso.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.auth0.android.result.Credentials
import com.routinew.espresso.R
import com.routinew.espresso.data.json.EspressoRestaurantListPacket
import com.routinew.espresso.data.json.EspressoRestaurantPacket
import com.routinew.espresso.data.json.ResultPacket
import com.routinew.espresso.objects.Restaurant
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

interface EspressoInterface {
    @GET("restaurants")
    fun getRestaurantList(
    ) : Call<EspressoRestaurantListPacket>

    @GET("restaurants/{id}")
    fun getRestaurant(
        @Path("id") restaurantId: Int
    ): Call<EspressoRestaurantPacket>

    @POST("restaurants/create")
     fun createRestaurant(
        @Body restaurant: Restaurant
     ) : Call<ResultPacket>

//    @PUT("restaurants/{id}")
//    fun updateRestaurant(@Path("id") restaurantId: Int)
}

object EspressoService {

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

    fun buildInterface(context: Context) {
        if (!initialized.getAndSet(true)) {
            // these won't change
            apiPath = context.getString(R.string.ESPRESSO_API_BASE)
            defaultServer = context.getString(R.string.ESPRESSO_DEV_SERVER)

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            with(sharedPreferences) {

                server = when (getBoolean("usecustomserverlocation", false)) {
                    true -> getString("customserverlocation", defaultServer)!!.also { Timber.i("Server Location $it") }
                    false -> defaultServer.also{ Timber.i("Default Location $it") }
                }
                registerOnSharedPreferenceChangeListener(setupListener)
            }
            createService(server, apiPath)
            this.context = context.applicationContext // It's alive forever so do this
        }
    }

    fun updateServerLoc(newServer: String, newApiPath: String = apiPath) {
        // call on prefs changed
        if (newApiPath != apiPath || newServer != server) {
            apiPath = newApiPath
            server = newServer
            createService(server, apiPath)
        }
    }

    private fun createService(server: String, apiPath: String) {
        Timber.d("Retrofit created : $server $apiPath")
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

            with(sharedPreferences) {
                val isCustom = getBoolean("usecustomserverlocation", false)
                val newServer = getString("customserverlocation", defaultServer)!!

                when (key) {
                    "usecustomserverlocation" -> updateServerLoc(if(isCustom) newServer else defaultServer)
                    "customserverlocation" -> updateServerLoc(newServer)
                }
            }

        } // method
    } // prefs listener
} // object
