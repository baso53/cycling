package com.comsysto.cycling.favorite

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "favorite")
data class FavoriteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var userId: String,
    var destinationName: String,

    var isFavorite: Boolean,

    @CreationTimestamp
    var created: Instant? = null,

    @UpdateTimestamp
    var lastModified: Instant? = null
)