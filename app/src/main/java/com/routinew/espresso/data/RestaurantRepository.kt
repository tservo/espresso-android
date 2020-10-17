package com.routinew.espresso.data

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.routinew.espresso.objects.Restaurant
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
        private val espressoService: EspressoService
){
    fun getRestaurants(): LiveData<List<Restaurant>> {
        val data = MutableLiveData<List<Restaurant>>()
        espressoService.getRestaurantList().enqueue(object: Callback<List<Restaurant>> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: retrofit2.Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                data.value = response.body()
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: retrofit2.Call<List<Restaurant>>, t: Throwable) {
                // TODO("Not yet implemented")
            }

        })
        return data
    }



}