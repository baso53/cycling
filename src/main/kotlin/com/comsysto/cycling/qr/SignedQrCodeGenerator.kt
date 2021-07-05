package com.comsysto.cycling.qr

import com.comsysto.cycling.encryption.RSAService
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.springframework.stereotype.Service
import java.io.OutputStream


@Service
class SignedQrCodeGenerator(
    private val rsaService: RSAService
) {

    companion object {
        private const val RSA_FIELD_NAME = "hash"
        private const val IMAGE_FORMAT = "png"
        private const val WIDTH = 2000
        private const val HEIGHT = 2000
    }

    fun writeQrCodeToOutputStream(fields: Map<String, String>, oStream: OutputStream) {
        val concatenatedFields = fields
            .entries
            .sortedBy { it.key }
            .joinToString()

        val rsaHash = rsaService.encryptToBase64(concatenatedFields)

        val fieldsWithRsa = fields.plus(RSA_FIELD_NAME to rsaHash)
            .entries
            .sortedBy { it.key }
            .joinToString()

        val matrix = MultiFormatWriter().encode(
            fieldsWithRsa,
            BarcodeFormat.QR_CODE,
            WIDTH,
            HEIGHT
        )

        MatrixToImageWriter.writeToStream(matrix, IMAGE_FORMAT, oStream)
    }
}