package com.routinew.espresso.ui.main


import androidx.lifecycle.ViewModel
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.RestaurantRepository
import com.routinew.espresso.objects.Restaurant


class MainViewModel : ViewModel() {

    val repository = RestaurantRepository(EspressoService.instance)

    // this will get the entire list of restaurants
    val restaurants = repository.getRestaurants()

    fun getRestaurants() {
        // we already have the livedata pipeline, so just update it
        repository.getRestaurants()
    }

    fun createRestaurant() {
        // take data and make restaurant
        // tell the repository
        val restaurant = Restaurant("The test place")
        repository.createRestaurant(restaurant)
    }
}