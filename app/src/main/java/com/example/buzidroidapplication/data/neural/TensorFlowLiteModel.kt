package com.example.buzidroidapplication.data.neural

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class TensorFlowLiteModel(
    private val context: Context
) {
    private var interpreter: Interpreter? = null

    init {
        try {
            interpreter = Interpreter(loadModel())
        } catch (e: Exception) {
            Log.e("${TensorFlowLiteModel::class.simpleName}", "An exception occurred: ${e.message}")
        }
    }

    private fun loadModel(): ByteBuffer {
        val assetManager = context.assets
        val assetFileDescriptor = assetManager.openFd(MODEL_PATH)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)

        return inputStream.channel.map(
            FileChannel.MapMode.READ_ONLY,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.declaredLength
        )
    }

    /**
     * A function which makes decision based on input value
     *
     *  @param input is the normalized image from your canvas in UI
     *  @param dsClassifierSize is the amount of predictable marks
     *  @return An index of predictable mark
     * **/
    fun getInference(
        input: Array<Array<Array<FloatArray>>>,
        dsClassifierSize: Int
    ): Int {
        if (interpreter == null) return -1

        val resultData = Array(1) { FloatArray(dsClassifierSize) }
        interpreter!!.run(input, resultData)

        val list = resultData[0].toList()

        return list.indexOf(list.max())
    }

    companion object {
        private const val MODEL_PATH = "model.tflite"
    }
}