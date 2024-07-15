package com.joybox.joyplug.utils

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

fun File.md5() : String {
    val digest = MessageDigest.getInstance("MD5")
    val buffer = ByteArray(1024)
    val inputStream = FileInputStream(this)
    inputStream.use {
        var read = it.read(buffer)
        while (read > 0) {
            digest.update(buffer)
            read = it.read(buffer)
        }
    }
    val md5 = digest.digest()
    return md5.joinToString("") { "%02x".format(it) }
}