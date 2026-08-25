package com.vaddshah2626.vetted.features.wishlist.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "photo_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        file.absolutePath // Returns the permanent local file path string
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun deleteImageFromInternalStorage(path : String) : Boolean {
    val file = File(path)
    if (file.exists()) {
        val deleted = file.delete()
        return deleted
    }
    return false
}