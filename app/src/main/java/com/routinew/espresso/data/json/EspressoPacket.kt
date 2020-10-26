package com.routinew.espresso.data.json

import com.routinew.espresso.objects.Restaurant

class EspressoPacket (
    val success: Boolean,
    val restaurants: List<Restaurant>
)