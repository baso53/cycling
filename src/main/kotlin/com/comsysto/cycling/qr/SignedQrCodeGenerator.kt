package com.comsysto.cycling.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.springframework.stereotype.Service
import java.io.OutputStream


@Service
class SignedQrCodeGenerator {

    companion object {
        private const val IMAGE_FORMAT = "png"
        private const val WIDTH = 2000
        private const val HEIGHT = 2000
    }

    fun writeQrCodeToOutputStream(value: String, oStream: OutputStream) {
        val matrix = MultiFormatWriter().encode(
            value,
            BarcodeFormat.QR_CODE,
            WIDTH,
            HEIGHT
        )

        MatrixToImageWriter.writeToStream(matrix, IMAGE_FORMAT, oStream)
    }
}