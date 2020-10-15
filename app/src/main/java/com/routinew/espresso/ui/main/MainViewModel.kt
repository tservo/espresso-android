package com.routinew.espresso.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.routinew.espresso.objects.Restaurant

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun getRestaurants() : LiveData<List<Restaurant>> {
        return MutableLiveData(listOf(
            Restaurant("joe"),
            Restaurant("brad"),
            Restaurant("steve").apply {
                street = "655 John Muir Dr"
                city = "San Francisco"
            }
        ))
    }
}