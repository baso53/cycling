package com.comsysto.cycling.destination

import org.springframework.data.repository.PagingAndSortingRepository

interface DestinationRepository : PagingAndSortingRepository<DestinationEntity, String> {
}