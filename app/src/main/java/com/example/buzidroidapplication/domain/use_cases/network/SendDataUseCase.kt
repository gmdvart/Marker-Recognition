package com.example.buzidroidapplication.domain.use_cases.network

import android.graphics.Bitmap
import com.example.buzidroidapplication.data.network.ImageCompressor
import com.example.buzidroidapplication.data.network.MarkerService
import com.example.buzidroidapplication.domain.util.MarkerSendResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendDataUseCase @Inject constructor(
    private val markerService: MarkerService,
    private val imageCompressor: ImageCompressor
) {
    suspend operator fun invoke(
        url: String,
        fileName: String,
        userName: String,
        bitmap: Bitmap,
        isRecognitionCorrect: Boolean
    ): Flow<MarkerSendResult> = flow {
        emit(MarkerSendResult.Idle)

        if (userName.isBlank()) {
            emit(MarkerSendResult.Failed("User name cannot by empty!"))
            return@flow
        }

        val suffix = if (isRecognitionCorrect) "_Pos_" else "_Neg_"

        val byteArray = imageCompressor.compressImage(bitmap)
        val result = markerService.sendMarker(url = url, fileName = suffix + fileName + "_$userName", bodyByteArray = byteArray)

        when (result) {
            is MarkerService.Result.Success<*> -> { emit(MarkerSendResult.Success) }
            is MarkerService.Result.Error -> { emit(MarkerSendResult.Failed(result.message ?: "An unknown cause occurred")) }
        }
    }
}