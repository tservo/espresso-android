package com.routinew.espresso.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.routinew.espresso.data.json.EspressoRestaurantPacket
import com.routinew.espresso.data.json.EspressoRestaurantListPacket
import com.routinew.espresso.data.json.ResultPacket
import com.routinew.espresso.data.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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
                call: Call<EspressoRestaurantListPacket>,
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
            override fun onFailure(call: Call<EspressoRestaurantListPacket>, t: Throwable) {
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
                call: Call<EspressoRestaurantPacket>,
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
            override fun onFailure(call: Call<EspressoRestaurantPacket>, t: Throwable) {
                // TODO("Not yet implemented")
                Timber.w(t)
            }

        })
        return singleRestaurant
    }

    fun createRestaurant(restaurant: Restaurant) : LiveData<Restaurant> {
        val returnedRestaurant = MutableLiveData<Restaurant>()

        espressoService.createRestaurant(restaurant).enqueue(object: Callback<ResultPacket> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */

            override fun onResponse(call: Call<ResultPacket>, response: Response<ResultPacket>) {

                if (response.isSuccessful) {
                    restaurant.id = response.body()?.id // this will be a nice swap I think

                    // cheat - put the new restaurant in the list
                    listOfRestaurants.value = listOfRestaurants.value?.plus(restaurant) ?: listOf(restaurant)
                } else {
                    // it didn't succeed.  How do we say something?
                }
                returnedRestaurant.value = restaurant
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ResultPacket>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return returnedRestaurant
    }

}