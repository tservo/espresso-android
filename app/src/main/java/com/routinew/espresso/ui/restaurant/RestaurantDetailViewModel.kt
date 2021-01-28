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

    private val repository = RestaurantRepository(EspressoService.instance)

    // this will keep the single restaurant
    lateinit var restaurant: LiveData<Restaurant>

    private var _selectedId = 0
    var selectedId : Int
        get() = _selectedId
        set(id)  {
            if (id != _selectedId) {
                _selectedId = id
                getRestaurant(id)
            }
        }

    fun getRestaurant(id: Int) {
        if (id == 0) return
        restaurant = repository.getRestaurant(id)
    }
}