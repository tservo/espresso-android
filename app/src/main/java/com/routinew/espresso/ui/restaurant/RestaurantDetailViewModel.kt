package com.routinew.espresso.ui.restaurant

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.RestaurantRepository
import com.routinew.espresso.objects.Restaurant
import javax.inject.Inject


class RestaurantDetailViewModel : ViewModel() {

    val repository = RestaurantRepository(EspressoService.instance)

    // this will keep the single restaurant
    lateinit var restaurant: LiveData<Restaurant>

    fun getRestaurant(id: Int): LiveData<Restaurant> {
        restaurant = repository.getRestaurant(id)
    }
}