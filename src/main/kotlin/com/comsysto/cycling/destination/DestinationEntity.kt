package com.comsysto.cycling.destination

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "destination")
data class DestinationEntity(
    @Id
    var name: String,
    var latitude: Float,
    var longitude: Float
)