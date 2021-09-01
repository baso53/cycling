package com.comsysto.cycling.confirmation

import javax.persistence.*

@Entity
@Table(
    name = "confirmation",
    uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "destinationName"])]
)
data class ConfirmationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var userId: String,
    var destinationName: String
)