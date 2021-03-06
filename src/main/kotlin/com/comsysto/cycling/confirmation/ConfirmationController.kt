package com.comsysto.cycling.confirmation

import com.comsysto.cycling.confirmation.inbound.InboundConfirmation
import com.comsysto.cycling.destination.DestinationEntity
import com.comsysto.cycling.encryption.RSAService
import com.comsysto.cycling.utils.fromStringToFieldMap
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException

@RestController
@RequestMapping("confirmation")
class ConfirmationController(
    private val rsaService: RSAService,
    private val confirmationRepository: ConfirmationRepository
) {
    companion object {
        val OBJECT_MAPPER = ObjectMapper()
        const val userId = "TO_BE_CONFIGURED_WITH_SPRING_SECURITY"
    }

    @PostMapping()
    fun tryConfirmation(@RequestBody confirmation: InboundConfirmation): ConfirmationEntity {
        val hashValue = try {
            rsaService.decryptFromBase64(confirmation.hash)
        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> throw ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "The input value is not a Base64 string."
                )
                is IllegalStateException, is IllegalBlockSizeException, is BadPaddingException -> throw ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Couldn't decrypt input value."
                )
                else -> throw throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.message
                )
            }
        }

        val map = fromStringToFieldMap(hashValue)

        val destinationEntity = try {
            OBJECT_MAPPER.convertValue(map, DestinationEntity::class.java)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "The input value contains invalid properties or is missing required properties."
            )
        }

        return try {
            confirmationRepository.save(
                ConfirmationEntity(
                    userId = userId,
                    destinationName = destinationEntity.name,
                    isConfirmed = true
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "User is already confirmed at the specified destination or destination doesn't exist."
            )
        }
    }
}

// can't convert from base64
// can't decrypt message
// can't convert message to data object
// already registered this user for this destination
// no destination with this name
