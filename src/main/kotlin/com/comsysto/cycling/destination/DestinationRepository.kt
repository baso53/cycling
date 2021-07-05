package com.comsysto.cycling.destination

import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface DestinationRepository : ReactiveSortingRepository<DestinationEntity, Int> {
}