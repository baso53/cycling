package com.comsysto.cycling.qr

import com.comsysto.cycling.encryption.RSAService
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.springframework.stereotype.Service
import java.io.File


@Service
class SignedQrCodeGenerator(
    private val rsaService: RSAService
) {

    companion object {
        private const val RSA_FIELD_NAME = "hash";
        private const val width = 2000
        private const val height = 2000
    }

    fun generateQrCode(fields: Map<String, String>) {
        val concatenatedFields = fields
            .entries
            .sortedBy { it.key }
            .joinToString()

        val rsaHash = rsaService.encrypt(concatenatedFields)

        val fieldsWithRsa = fields.plus(RSA_FIELD_NAME to rsaHash)
            .entries
            .sortedBy { it.key }
            .joinToString()

        val matrix = MultiFormatWriter().encode(
            fieldsWithRsa,
            BarcodeFormat.QR_CODE,
            width,
            height
        )

        MatrixToImageWriter.writeToFile(
            matrix,
            "sebo.jpg".substring("sebo.jpg".lastIndexOf('.') + 1),
            File("sebo.jpg")
        )
    }
}