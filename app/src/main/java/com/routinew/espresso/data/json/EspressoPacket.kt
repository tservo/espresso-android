package com.routinew.espresso.data.json

import com.routinew.espresso.data.model.Restaurant
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EspressoRestaurantListPacket  (
    val success: Boolean,
    val api_version: Int? = null,
    val message: String? = null,
    val restaurants: List<Restaurant>?= null
)

@JsonClass(generateAdapter = true)
data class EspressoRestaurantPacket (
    val success: Boolean,
    val api_version: Int? = null,
    val message: String? = null,
    val restaurant: Restaurant?= null
)

/**
 * This class
 */
@JsonClass(generateAdapter = true)
data class ResultPacket (
    val success: Boolean,
    val api_version: Int? = null,
    val message: String? = null,
    val id: Int? = null // for successful creation
)