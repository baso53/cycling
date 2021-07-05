package com.comsysto.cycling

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.FileOutputStream
import java.security.KeyPairGenerator

@Disabled
class CreatePublicPrivateKeyPair {

    @Test
    fun generateKeyPair() {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        val pair = generator.generateKeyPair()

        val privateKey = pair.private
        val publicKey = pair.public

        FileOutputStream("public.key").use { fos -> fos.write(publicKey.encoded) }
        FileOutputStream("private.key").use { fos -> fos.write(privateKey.encoded) }
    }

}
