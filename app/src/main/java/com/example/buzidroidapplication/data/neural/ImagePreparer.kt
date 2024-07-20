package com.example.buzidroidapplication.data.neural

import android.graphics.Bitmap

class ImagePreparer {
    private fun prepareImage(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
        val inputValue = initializeInputValue()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, false)
        val scaledWidth = scaledBitmap.width
        val scaledHeight = scaledBitmap.height

        var intPixels = IntArray(scaledWidth * scaledHeight)

        scaledBitmap.getPixels(intPixels, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)

        intPixels = dilateImage(intPixels, scaledWidth, scaledHeight)

        for (i in 0 until scaledHeight) {
            for (j in 0 until scaledWidth) {
                val pixel = intPixels[i * IMAGE_WIDTH + j]

                inputValue[0][i][j] = floatArrayOf(
                    ((pixel and 0xFF) - IMAGE_MEAN) / IMAGE_STD
                )
            }
        }

        return inputValue
    }

    private fun initializeInputValue(): Array<Array<Array<FloatArray>>> {
        val columns = Array(64) { FloatArray(1) }
        val rows = Array(64) { columns.clone() }
        return Array(1) { rows.clone() }
    }

    private fun dilateImage(
        pixels: IntArray,
        width: Int,
        height: Int,
    ): IntArray {
        val outputPixels = IntArray(width * height)

        fun isThicknessInBounds(
            startX: Int,
            startY: Int,
            endX: Int,
            endY: Int
        ) = startX >= 0 && startY >= 0 && endX < width && endY < height

        fun fillThickness(startX: Int, startY: Int, endX: Int, endY: Int) {
            for (i in startY until endY) {
                for (j in startX until endX)
                    outputPixels[i * width + j] = 1
            }
        }

        for (i in 0 until height) {
            for (j in 0 until width) {
                val sourcePixelValue = pixels[i * width + j]
                if (sourcePixelValue != 0) {
                    val startX = j - THICKNESS_VALUE
                    val startY = i - THICKNESS_VALUE

                    val endX = j + THICKNESS_VALUE
                    val endY = i + THICKNESS_VALUE

                    if (isThicknessInBounds(startX, startY, endX, endY)) fillThickness(startX, startY, endX, endY)
                    else pixels[i * width + j] = sourcePixelValue
                }
            }
        }

        return outputPixels
    }

    companion object {
        private const val IMAGE_WIDTH = 64
        private const val IMAGE_HEIGHT = 64
        private const val THICKNESS_VALUE = 2
        private const val IMAGE_MEAN = 0.0f
        private const val IMAGE_STD = 1.0f
    }
}