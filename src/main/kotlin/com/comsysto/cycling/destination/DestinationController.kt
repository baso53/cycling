package com.comsysto.cycling.destination

import com.comsysto.cycling.encryption.RSAService
import com.comsysto.cycling.qr.SignedQrCodeGenerator
import com.comsysto.cycling.utils.toStringWithRsaField
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse

val <T> Optional<T>.value: T?
    get() = orElse(null)

@RestController
@RequestMapping("destinations")
class DestinationController(
    private val destinationRepository: DestinationRepository,
    private val qrCodeGenerator: SignedQrCodeGenerator,
    private val rsaService: RSAService
) {

    @GetMapping(produces = [MediaType.TEXT_HTML_VALUE])
    fun getAllHtml(): String {
        return destinationRepository.findAll()
            .fold("") { prev, curr -> "$prev <br> <a href=\"destinations/${curr.name}\">Get QR code for ${curr.name}</a>" }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAll(): Iterable<DestinationEntity> {
        return destinationRepository.findAll()
    }

    @GetMapping(path = ["{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getById(@PathVariable id: String, servletResponse: HttpServletResponse) {
        destinationRepository.findById(id).value?.run {
            val qrCodeValue = toStringWithRsaField(
                mapOf(
                    "name" to name,
                    "latitude" to latitude.toString(),
                    "longitude" to longitude.toString(),
                )
            )

            val encryptedQrCodeValue = rsaService.encryptToBase64(qrCodeValue)

            qrCodeGenerator.writeQrCodeToOutputStream(
                encryptedQrCodeValue,
                servletResponse.outputStream
            )
        }
    }
}