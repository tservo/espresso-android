package com.routinew.espresso.ui.compose

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.RestaurantRepository
import com.routinew.espresso.data.model.Restaurant

class ComposeRestaurantViewModel: ViewModel() {

    private val repository = RestaurantRepository(EspressoService.instance)

    val name = MutableLiveData<String>().apply { value = "" }
    val dateest = MutableLiveData<String>().apply { value = "" }
    val address = MutableLiveData<String>().apply { value = "" }
    val suite = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val state = MutableLiveData<String>().apply { value = "" }
    val zip = MutableLiveData<String>().apply { value = "0"}
    val website = MutableLiveData<String>().apply { value = "" }
    val phone = MutableLiveData<String>().apply { value = "" }

    val isRestaurantValid = MediatorLiveData<Boolean>()

    init {
        isRestaurantValid.run {
            addSource(name) {
                value = validateRestaurant()
            }
            addSource(zip) {
                value = validateRestaurant()
            }
        }
    }

    fun createRestaurant() {
        if (isRestaurantValid.value != true) return // need a valid name

        val name = this.name.value!!
        val restaurant = Restaurant(
            name,
            "test@test",
            null,
            dateest.value,
            "test@test", // pull this from user credentials
            address.value,
            suite.value,
            city.value,
            state.value,
            zip.value?.toInt(),
            website.value,
            phone.value
        )

        repository.createRestaurant(restaurant)
    }

    private fun validateRestaurant(): Boolean {
        val name = this.name.value
        if (null == name || name == "") return false

        if (zip.value == "" || (zip.value?.isDigitsOnly() == false )) return false
        return true
    }


}