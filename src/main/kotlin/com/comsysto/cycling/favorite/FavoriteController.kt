package com.comsysto.cycling.favorite

import com.comsysto.cycling.destination.value
import com.comsysto.cycling.favorite.incoming.IncomingFavorite
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("favorite")
class FavoriteController(
    private val favoriteRepository: FavoriteRepository
) {

    @PostMapping()
    fun tryFavorite(@RequestBody request: IncomingFavorite): FavoriteEntity {

        val userId = "TO_BE_CONFIGURED_WITH_SPRING_SECURITY"

        return try {
            favoriteRepository.findByDestinationNameAndUserId(request.destinationName, userId)
                .value?.run {
                    favoriteRepository.save(
                        this.also { it.isFavorite = request.favorite }
                    )
                } ?: favoriteRepository.save(
                FavoriteEntity(
                    userId = userId,
                    destinationName = request.destinationName,
                    isFavorite = request.favorite
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Destination doesn't exist."
            )
        }
    }
}
