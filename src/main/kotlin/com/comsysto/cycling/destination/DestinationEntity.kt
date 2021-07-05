package com.comsysto.cycling.destination

import org.springframework.data.relational.core.mapping.Table

@Table("destination")
data class DestinationEntity(
    val id: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
)