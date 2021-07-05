package com.comsysto.cycling.destination

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("destinations")
class DestinationController(
    private val destinationRepository: DestinationRepository
) {

    @GetMapping
    fun getAll(): Flux<DestinationEntity> {
        return destinationRepository.findAll();
    }
}