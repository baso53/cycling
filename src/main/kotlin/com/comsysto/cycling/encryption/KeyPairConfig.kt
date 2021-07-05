package com.comsysto.cycling.encryption

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Configuration
class KeyPairConfig {

    private val keyFactoryInstance = KeyFactory.getInstance("RSA")

    private fun getFileBytes(resourceLocation: String): ByteArray {
        val keyFile = ResourceUtils.getFile(resourceLocation)
        return keyFile.readBytes()
    }

    @Bean
    fun publicRSAKey(): PublicKey {
        val publicKeyBytes = getFileBytes("classpath:public.key")
        val publicKeySpec = X509EncodedKeySpec(publicKeyBytes)

        return keyFactoryInstance.generatePublic(publicKeySpec)
    }

    @Bean
    fun privateRSAKey(): PrivateKey {
        val privateKeyBytes = getFileBytes("classpath:private.key")
        val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)

        return keyFactoryInstance.generatePrivate(privateKeySpec)
    }
}