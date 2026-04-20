package edu.ucsb.cs156.autograderproxy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.apache.commons.codec.digest.DigestUtils
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.io.encoding.Base64

@JvmInline
value class HashedApiKey(val key: String)

@Entity
data class StoredApiKey(@Id var hash: HashedApiKey)

data class GeneratedApiKey(val key: String, val hash: HashedApiKey){

}

fun generateApiKey(): GeneratedApiKey {
    val generator = SecureRandom()
    val bytes = ByteArray(32)
    generator.nextBytes(bytes)
    val readableKey = Base64.Default.encode(bytes)
    val hash = DigestUtils.sha256Hex(bytes)
    return GeneratedApiKey(
        key = readableKey,
        hash = HashedApiKey(hash)
    )
}
