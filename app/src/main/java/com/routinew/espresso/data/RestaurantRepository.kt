package com.routinew.espresso.data

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.routinew.espresso.data.json.EspressoPacket
import com.routinew.espresso.objects.Restaurant
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RestaurantRepository /* @Inject */ constructor(
        private val espressoService: EspressoInterface
){
    val data = MutableLiveData<List<Restaurant>>()

    fun getRestaurants(): LiveData<List<Restaurant>> {
        espressoService.getRestaurantList().enqueue(object: Callback<EspressoPacket> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: retrofit2.Call<EspressoPacket>,
                response: Response<EspressoPacket>
            ) {
                Timber.i(response.toString())
                data.value = response.body()?.restaurants
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: retrofit2.Call<EspressoPacket>, t: Throwable) {
                // TODO("Not yet implemented")
                Timber.w(t)
            }

        })
        return data
    }



}