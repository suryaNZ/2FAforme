package com.surya.a2faforme

//import kotlin.io.encoding.Base64
//import java.io.FileInputStream
import android.app.Application
import android.util.Log
import java.io.File
import org.apache.commons.codec.binary.Base32


class A2FAforme : Application() {
    override fun onCreate() {
        super.onCreate()
        keystoreInit()
//        checks for TOTP DIR, and initialises it with a test file if it doesn't exist
        val TOTP_DIR = File(filesDir.absolutePath + File.separator + "TOTP_FILES")
//        storeNewKey("otpauth://totp/alice@google.com?secret=JBSWY3DPEHPK3PXP&issuer=Google&digits=6&period=30", TOTP_DIR)
//        TOTP_DIR.listFiles().forEach { file ->
//            Log.d("TOTP_TEST_KEY", "file found: " + file.name)
//            file.forEachLine { line ->
//                Log.d("TOTP_KEY_DATA", file.name + " -> " + line)
//            }
//        }


//        val b32 = Base32()
//        val decoded = b32.decode("HVR4CFHAFOWFGGFAGSA5JVTIMMPG6GMT")
//        Log.d("TOTP_GENERATE", generateTOTP(
//            decoded.joinToString("") { "%02x".format(it) },
//            System.currentTimeMillis(),
//            "30",
//            "6",
//            "HmacSHA1")
//        )

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

