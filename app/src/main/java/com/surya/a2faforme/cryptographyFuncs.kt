package com.surya.a2faforme

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private const val KEY_ALIAS = "2FAFORME_KEY"

fun keystoreInit() {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {load(null)}
    if(!keyStore.containsAlias(KEY_ALIAS)) generateSecretKey()
}

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

fun encryptData(plaintext:String): Pair<String, String> {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {load(null)}
    val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
    val secretKey: SecretKey = secretKeyEntry.secretKey

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val iv = cipher.iv
    val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))

    return Pair(
        Base64.encodeToString(ciphertext, Base64.NO_WRAP),
        Base64.encodeToString(iv, Base64.NO_WRAP)
    )
}

fun decryptData(ciphertextB64:String, ivB64:String): String {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {load(null)}
    val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
    val secretKey: SecretKey = secretKeyEntry.secretKey

    val iv = Base64.decode(ivB64, Base64.NO_WRAP)
    val ciphertext = Base64.decode(ciphertextB64, Base64.NO_WRAP)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val spec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

    val plaintextbytes = cipher.doFinal(ciphertext)
    return String(plaintextbytes, Charsets.UTF_8)
}