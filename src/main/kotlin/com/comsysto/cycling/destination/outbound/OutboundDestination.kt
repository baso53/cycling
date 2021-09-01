package com.comsysto.cycling.destination.outbound

data class OutboundDestination(
    var name: String,
    var latitude: Float,
    var longitude: Float,

    var isConfirmed: Boolean,
    var isFavorite: Boolean
)