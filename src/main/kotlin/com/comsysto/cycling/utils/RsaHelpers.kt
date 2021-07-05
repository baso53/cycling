package com.comsysto.cycling.utils

import com.comsysto.cycling.encryption.RSAService
import org.springframework.stereotype.Service

@Service
class RsaHelpers(
    val rsaService: RSAService
) {

    companion object {
        private const val RSA_FIELD_NAME = "hash"
    }

    fun toStringWithRsaField(fields: Map<String, String>): String {
        val concatenatedFields = fields
            .entries
            .joinToString()

        val rsaHash = rsaService.encryptToBase64(concatenatedFields)

        return fields.plus(RSA_FIELD_NAME to rsaHash)
            .entries
            .joinToString()
    }

    fun fromStringToFieldMap(value: String): Map<String, String> =
        value.split(", ").associate {
            val (left, right) = it.split("=")
            left to right
        }
}