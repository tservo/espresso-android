package com.routinew.espresso.objects

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Restaurant(var name: String, var id: Int) {
    var date_established: String? = null
    var email: String? = null
    var street: String? = null
    var suite: String? = null
    var city: String? = null
    var state: String? = null
    var zip_code: Int? = null
    var website: String? = null
    var phone: String? = null



}


