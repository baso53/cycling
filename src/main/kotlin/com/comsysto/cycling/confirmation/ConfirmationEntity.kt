package com.comsysto.cycling.confirmation

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "confirmation")
data class ConfirmationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var userId: String,
    var destinationName: String,

    var isConfirmed: Boolean,

    @CreationTimestamp
    var created: Instant? = null,

    @UpdateTimestamp
    var lastModified: Instant? = null
)