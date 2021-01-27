package com.routinew.espresso.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.routinew.espresso.data.json.EspressoRestaurantPacket
import com.routinew.espresso.data.json.EspressoRestaurantListPacket
import com.routinew.espresso.objects.Restaurant
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RestaurantRepository /* @Inject */ constructor(
        private val espressoService: EspressoInterface
){
    val listOfRestaurants = MutableLiveData<List<Restaurant>>()
    val singleRestaurant = MutableLiveData<Restaurant>()

    fun getRestaurants(): LiveData<List<Restaurant>> {
        espressoService.getRestaurantList().enqueue(object: Callback<EspressoRestaurantListPacket> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: retrofit2.Call<EspressoRestaurantListPacket>,
                response: Response<EspressoRestaurantListPacket>
            ) {
                Timber.i(response.toString())
                val body = response.body()

                listOfRestaurants.value =
                    if (body?.success == true) {
                        body.restaurants ?: emptyList()
                    }
                    else {
                        emptyList()
                    }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: retrofit2.Call<EspressoRestaurantListPacket>, t: Throwable) {
                Timber.w(t)
                listOfRestaurants.value = emptyList()

            }

        })
        return listOfRestaurants
    }

    fun getRestaurant(id : Int) : LiveData<Restaurant> {
        espressoService.getRestaurant(id).enqueue(object: Callback<EspressoRestaurantPacket> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: retrofit2.Call<EspressoRestaurantPacket>,
                response: Response<EspressoRestaurantPacket>
            ) {
                Timber.i(response.toString())
                val body = response.body()

                singleRestaurant.value =
                    if (body?.success == true) {
                        body.restaurant
                    }
                    else {
                        null
                    }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: retrofit2.Call<EspressoRestaurantPacket>, t: Throwable) {
                // TODO("Not yet implemented")
                Timber.w(t)
            }

        })
        return singleRestaurant
    }



}