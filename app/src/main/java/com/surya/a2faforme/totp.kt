package com.surya.a2faforme

import org.apache.commons.codec.binary.Base32
import java.math.BigInteger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private fun hmac_sha(crypto: String, keyBytes: ByteArray, text: ByteArray): ByteArray {
    val hmac: Mac = Mac.getInstance(crypto)
    val macKey: SecretKeySpec = SecretKeySpec(keyBytes, crypto)
    hmac.init(macKey)
    return hmac.doFinal(text)
}

private fun hexStrToBytes(hex: String): ByteArray {
    val bArray:ByteArray = BigInteger("10$hex", 16).toByteArray()
    val ret: ByteArray = ByteArray(bArray.size - 1)
    (0..ret.size-1).forEach { i ->
        ret[i] = bArray[i+1]
    }
    return ret
}

private val DIGITS_POWER =
    intArrayOf(1,10,100,1000,10000,100000,1000000,10000000,100000000)

val ALGO = mapOf<String, String>(
    "SHA1" to "HmacSHA1",
    "SHA256" to "HmacSHA256",
    "SHA512" to "HmacSHA512",
)
// TODO return pair of code and time remaining
fun generateTOTP(
    keyGiven:String,
    time: Long,
    period: String,
    retDigits: String,
    cryptoGiven: String): String {
    val codeDigits:Int = Integer.decode(retDigits).toInt()
//    val result: String = ""

    val b32 = Base32()
    val decoded = b32.decode(keyGiven)
    val key = decoded.joinToString("") { "%02x".format(it) }

    val crypto = ALGO[cryptoGiven]!!

    val paddedTime = ((time/1000L)/period.toInt())
        .toString(16)
        .padStart(16, '0')

    val msg: ByteArray = hexStrToBytes(paddedTime)
    val k: ByteArray = hexStrToBytes(key)
    val hash: ByteArray = hmac_sha(crypto, k, msg)

    val offset:Int = hash[hash.size - 1].toInt() and 0xf
    val binary:Int =
        ((hash[offset].toInt() and 0x7f) shl 24) or
        ((hash[offset + 1].toInt() and 0xff) shl 16) or
        ((hash[offset + 2].toInt() and 0xff) shl 8) or
        ((hash[offset + 3].toInt() and 0xff))

    val otp:Int = binary % DIGITS_POWER[codeDigits]

    val result:String  = otp.toString().padStart(codeDigits, '0')
    return result


}