package com.comsysto.cycling.destination

import com.comsysto.cycling.confirmation.ConfirmationEntity
import com.comsysto.cycling.favorite.FavoriteEntity
import javax.persistence.*

@Entity
@Table(name = "destination")
data class DestinationEntity(
    @Id
    var name: String,
    var latitude: Float,
    var longitude: Float,

    @OneToMany(mappedBy = "destinationName")
    var confirmationEntity: List<ConfirmationEntity>,

    @OneToMany(mappedBy = "destinationName")
    var favoriteEntity: List<FavoriteEntity>
)