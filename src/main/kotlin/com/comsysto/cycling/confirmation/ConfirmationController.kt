package com.comsysto.cycling.confirmation

import com.comsysto.cycling.confirmation.incoming.IncomingConfirmation
import com.comsysto.cycling.encryption.RSAService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("confirmation")
class ConfirmationController(
    private val rsaService: RSAService,
    private val confirmationRepository: ConfirmationRepository
) {

    @PostMapping()
    fun tryConfirmation(@RequestBody confirmation: IncomingConfirmation): Mono<ConfirmationEntity> {
        val hashValue = rsaService.decryptFromBase64(confirmation.hash)

        val map = hashValue.split(", ").associate {
            val (left, right) = it.split("=")
            left to right
        }

        return confirmationRepository.save(
            ConfirmationEntity(
                userId = "sebo",
                destinationId = 1
            )
        )
    }
}