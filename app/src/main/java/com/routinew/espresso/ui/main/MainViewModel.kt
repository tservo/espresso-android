package com.routinew.espresso.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.routinew.espresso.data.RestaurantRepository
import com.routinew.espresso.objects.Restaurant


class MainViewModel @ViewModelInject constructor(
    private val restaurantRepository: RestaurantRepository,
    @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    val restaurants = restaurantRepository.getRestaurants()

    // TODO: Implement the ViewModel
//    fun getRestaurants() : LiveData<List<Restaurant>> {
////        return MutableLiveData(listOf(
////            Restaurant("joe"),
////            Restaurant("brad"),
////            Restaurant("steve").apply {
////                street = "655 John Muir Dr"
////                city = "San Francisco"
////            }
////        ))
//
//        return restaurants
//    }

}