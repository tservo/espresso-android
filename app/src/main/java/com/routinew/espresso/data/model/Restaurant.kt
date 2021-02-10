package com.routinew.espresso.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Restaurant(var name: String,
    var id: Int? = null,
    var date_established: String? = null,
    var email: String? = null,
    var street: String? = null,
    var suite: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zip_code: Int? = null,
    var website: String? = null,
    var phone: String? = null
)



