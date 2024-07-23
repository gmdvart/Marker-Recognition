package com.example.buzidroidapplication.data.network

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCompressor @Inject constructor(){
    fun compressImage(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(BITMAP_COMPRESS_FORMAT, BITMAP_COMPRESS_QUALITY, outputStream)
        return outputStream.toByteArray()
    }

    companion object {
        private val BITMAP_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG
        private const val BITMAP_COMPRESS_QUALITY = 50
    }
}