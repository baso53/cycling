package com.comsysto.cycling.confirmation

import org.springframework.data.repository.PagingAndSortingRepository

interface ConfirmationRepository : PagingAndSortingRepository<ConfirmationEntity, Int> {
}