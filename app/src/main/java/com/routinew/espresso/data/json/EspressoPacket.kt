package com.routinew.espresso.data.json

import com.routinew.espresso.objects.Restaurant
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EspressoPacket (
    val success: Boolean,
    val restaurants: List<Restaurant>
)