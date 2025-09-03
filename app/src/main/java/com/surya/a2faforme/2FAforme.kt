package com.surya.a2faforme

//import kotlin.io.encoding.Base64
//import java.io.FileInputStream
import android.app.Application
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.security.KeyStore



class A2FAforme : Application() {
    override fun onCreate() {
        super.onCreate()
        keystoreInit()
//        checks for TOTP DIR, and initialises it with a test file if it doesn't exist
        val TOTP_DIR = File(filesDir.absolutePath + File.separator + "TOTP_FILES")
        storeNewKey("otpauth://totp/Google:alice@google.com?secret=JBSWY3DPEHPK3PXP&issuer=Google&digits=6&period=30", TOTP_DIR)
        TOTP_DIR.listFiles().forEach { file ->
            Log.d("TOTP_TEST_KEY", "file found: " + file.name)
            file.forEachLine { line ->
                Log.d("TOTP_KEY_DATA", file.name + " -> " + line)
            }
        }

//        Log.d("TOTP_RET_KEYS", retrieveKeys(TOTP_DIR).toString())

//        val original = "test_2fa_secret"
//
//        val (encrypted, iv) = encryptData(original)
//        val decrypted = decryptData(encrypted, iv)
//
//        Log.d("TEST_2FA", "Original:  $original")
//        Log.d("TEST_2FA", "Encrypted: $encrypted")
//        Log.d("TEST_2FA", "IV:        $iv")
//        Log.d("TEST_2FA", "Decrypted: $decrypted")

    }
}

