package com.routinew.espresso.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.RestaurantRepository
import com.routinew.espresso.objects.Restaurant
import javax.inject.Inject


class MainViewModel : ViewModel() {

    val repository = RestaurantRepository(EspressoService.instance)

    // this will get the entire list of restaurants
    val restaurants = repository.getRestaurants()

    fun getRestaurant(id: Int): LiveData<Restaurant> {
        return repository.getRestaurant(id)
    }
}