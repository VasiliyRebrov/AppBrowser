package com.example.feature.appinfos.impl.util

import java.io.FileInputStream
import java.security.MessageDigest

internal object HashUtil {

// MARK: - Methods

    fun sha256HashString(path: String): String? {
        return try {
            val digest = MessageDigest.getInstance(Algorithm.SHA_256)

            FileInputStream(path).use { fis ->
                val buffer = ByteArray(Buffer.SIZE)
                var bytesRead: Int

                while (true) {
                    bytesRead = fis.read(buffer)

                    when (bytesRead != -1) {
                        true -> digest.update(buffer, 0, bytesRead)
                        else -> break
                    }
                }
            }

            val hashBytes = digest.digest()

            buildString {
                hashBytes.forEach { byte ->
                    val hexValue = String.format(Format.HEX_BYTE, byte)
                    append(hexValue)
                }
            }
        }
        catch (ex: Exception) {
            null
        }
    }

// MARK: - Constants

    private object Algorithm {
        const val SHA_256 = "SHA-256"
    }

    private object Buffer {
        const val SIZE = 8192
    }

    private object Format {
        const val HEX_BYTE = "%02x"
    }
}
