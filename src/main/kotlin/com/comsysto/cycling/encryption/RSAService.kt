package com.comsysto.cycling.encryption

import org.springframework.stereotype.Service
import java.security.PrivateKey
import java.security.PublicKey
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

    fun encrypt(rawMessage: String): String {
        val rawMessageBytes = rawMessage.toByteArray()
        val encryptedMessageBytes = encryptCipher.doFinal(rawMessageBytes)

        return String(encryptedMessageBytes)
    }

    fun decrypt(encryptedMessage: String): String {
        val encryptedMessageBytes = encryptedMessage.toByteArray()
        val decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes)

        return String(decryptedMessageBytes)
    }
}