package com.comsysto.cycling.encryption

import org.springframework.stereotype.Service
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Base64
import javax.crypto.Cipher

@Service
class RSAService(
    privateKey: PrivateKey,
    publicKey: PublicKey
) {
    private val encryptCipher = Cipher.getInstance("RSA")
    private val decryptCipher = Cipher.getInstance("RSA")

    init {
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey)
        decryptCipher.init(Cipher.DECRYPT_MODE, publicKey)
    }

    fun encryptToBase64(rawMessage: String): String {
        val rawMessageBytes = rawMessage.toByteArray()
        val encryptedMessageBytes = encryptCipher.doFinal(rawMessageBytes)

        return Base64.getEncoder().encodeToString(encryptedMessageBytes)
    }

    fun decryptFromBase64(encryptedMessageBase64: String): String {
        val encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessageBase64)
        val decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes)

        return String(decryptedMessageBytes)
    }
}