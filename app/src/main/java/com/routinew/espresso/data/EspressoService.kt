package com.routinew.espresso.data

import com.routinew.espresso.objects.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspressoService {
    @GET("/restaurants")
    fun getRestaurantList() : Call<List<Restaurant>>

    @GET("/restaurant/{id}")
    fun getRestaurant(@Path("id") restaurantId: Int): Call<Restaurant>

//    @POST("/restaurants/create")
//    fun createRestaurant = TODO()

//    @PUT("/restaurants/{id}")
//    fun updateRestaurant(@Path("id") restaurantId: Int)
}
