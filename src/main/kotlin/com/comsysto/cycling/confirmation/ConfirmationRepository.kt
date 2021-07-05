package com.comsysto.cycling.confirmation

import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface ConfirmationRepository : ReactiveSortingRepository<ConfirmationEntity, Int> {
}