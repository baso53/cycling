package com.comsysto.cycling.confirmation

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("confirmation")
data class ConfirmationEntity(
    @Id val id: Int? = null,
    val userId: String,
    val destinationId: Int
)