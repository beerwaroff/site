package forum.model.security

import java.lang.StringBuilder
import java.security.MessageDigest

object Hashing {

    fun getHash(inByte: ByteArray, type: String = "SHA-256"): String {
        val digestedBytes = MessageDigest.getInstance(type).digest(inByte)
        return with(StringBuilder()) {
            digestedBytes.forEach {
                b -> append(String.format("%02x", b))
            }
            toString().toLowerCase()
        }
    }
}