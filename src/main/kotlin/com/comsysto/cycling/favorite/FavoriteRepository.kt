package com.comsysto.cycling.favorite

import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface FavoriteRepository : PagingAndSortingRepository<FavoriteEntity, Int> {

    fun findByDestinationNameAndUserId(destinationName: String, userId: String): Optional<FavoriteEntity>
}