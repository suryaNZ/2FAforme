package com.surya.a2faforme

import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun storeNewKey(qrString:String, TOTP_DIR: File, customName:String=""): Boolean {
//    example QR string: otpauth://totp/Google:alice@google.com?secret=JBSWY3DPEHPK3PXP&issuer=Google&digits=6&period=30
//    val TOTP_DIR = File(filesDir.absolutePath + File.separator + "TOTP_FILES")
    try {
        val qrValsUnparsed = qrString.split('?')[1].split('&')
        val qrVals = mutableMapOf<String, String>()

//    sets default values
        qrVals["period"] = "30"
        qrVals["digits"] = "6"
        qrVals["algorithm"] = "SHA1"
        qrVals["issuer"] = ""
        if(customName == "") {
            qrVals["label"] = qrString.split('?')[0].split("/").last()
        } else {
            qrVals["label"] = customName
        }

        qrValsUnparsed.forEach { qrval ->
            val key = qrval.split('=')[0]
            val value = qrval.split('=')[1]
            qrVals[key] = value
        }

//    does encryption
        if(!TOTP_DIR.exists()) {
            TOTP_DIR.mkdir()
        }
        val keyFile = File(TOTP_DIR.absolutePath+File.separator + System.currentTimeMillis())
        keyFile.createNewFile()
        val keyFileWriter = FileWriter(keyFile)

        val (encrypted, iv) = encryptData(qrVals["secret"]!!)
        qrVals["secret"] = encrypted
        qrVals["iv"] = iv
        qrVals["qrText"] = qrString
        qrVals.forEach { key, value ->
            keyFileWriter.appendLine("$key=$value")
        }
        keyFileWriter.close()

        Log.d("TOTP_STORE_NEW_KEY", "key stored")
        return true
    } catch (e: Exception) {
        return false
    }
}


fun retrieveKeys(TOTP_DIR: File): List<Map<String, String>> {
    val mutList = mutableListOf<Map<String, String>>()
    TOTP_DIR.listFiles()?.forEach { file: File ->
        val res = mutableMapOf<String, String>()
        val inputStream = file.inputStream()
        inputStream.bufferedReader().forEachLine {
            val index = it.indexOf('=')
            val key = it.substring(0, index)
            val value = it.substring(index + 1)
            res[key] = value
        }
        res["secret"] = decryptData(
            ciphertextB64 = res["secret"]!!,
            ivB64 = res["iv"]!!,
        )
        mutList.add(res)
    }
    return mutList.toList()
//    return res
}