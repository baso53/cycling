package com.comsysto.cycling.destination

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("destination")
data class DestinationEntity(
    @Id val id: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
)