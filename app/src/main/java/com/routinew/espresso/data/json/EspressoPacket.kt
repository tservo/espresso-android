package com.routinew.espresso.data.json

import com.routinew.espresso.objects.Restaurant
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EspressoRestaurantListPacket  (
    val success: Boolean,
    val version: Int? = null,
    val message: String? = null,
    val restaurants: List<Restaurant>?= null
)

@JsonClass(generateAdapter = true)
data class EspressoRestaurantPacket (
    val success: Boolean,
    val version: Int? = null,
    val message: String? = null,
    val restaurant: Restaurant?= null
)