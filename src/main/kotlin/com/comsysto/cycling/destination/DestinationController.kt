package com.comsysto.cycling.destination

import com.comsysto.cycling.qr.SignedQrCodeGenerator
import com.comsysto.cycling.utils.RsaHelpers
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("destinations")
class DestinationController(
    private val destinationRepository: DestinationRepository,
    private val qrCodeGenerator: SignedQrCodeGenerator,
    private val rsaHelpers: RsaHelpers
) {

    @GetMapping(produces = [MediaType.TEXT_HTML_VALUE])
    fun getAll(): Mono<String> {
        return destinationRepository.findAll()
            .reduce("") { prev, curr ->
                "$prev <br> <a href=\"destinations/${curr.id}\">Get QR code for ${curr.name} (${curr.id})</a>"
            }
    }

    @GetMapping(path = ["{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getById(@PathVariable id: Int): Mono<DataBuffer> {
        return destinationRepository.findById(id)
            .map { destination ->
                val dataBuffer = DefaultDataBufferFactory().allocateBuffer()

                dataBuffer.asOutputStream().use { outputStream ->
                    val qrCodeValue = rsaHelpers.toStringWithRsaField(
                        mapOf(
                            "name" to destination.name,
                            "latitude" to destination.latitude.toString(),
                            "longitude" to destination.longitude.toString(),
                        )
                    )

                    qrCodeGenerator.writeQrCodeToOutputStream(
                        qrCodeValue,
                        outputStream
                    )
                }

                dataBuffer
            }
    }
}