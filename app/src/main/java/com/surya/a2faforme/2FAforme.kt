package com.surya.a2faforme

import android.app.Application
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
//import kotlin.io.encoding.Base64
import android.util.Base64

private const val KEY_ALIAS = "TOTP_SECRET_KEY"

class A2FAforme : Application() {
    override fun onCreate() {
        super.onCreate()
        generateSecretKey()
    }
}

//fun initKey() {
//    if(!keyExists(key))
//}

fun generateSecretKey() {
    val keygen: KeyGenerator = KeyGenerator
        .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
    val keygenParameterSpec = KeyGenParameterSpec.Builder(
        KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setRandomizedEncryptionRequired(true)
        .build()

    keygen.init(keygenParameterSpec)
    keygen.generateKey()
}

fun ecryptData(plaintext:String): Pair<String, String> {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {load(null)}
    val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
    val secretKey: SecretKey = secretKeyEntry.secretKey

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val iv = cipher.iv
    val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))

    return Pair(
        Base64.encodeToString(ciphertext, Base64.DEFAULT),
        Base64.encodeToString(iv, Base64.DEFAULT)
    )
}