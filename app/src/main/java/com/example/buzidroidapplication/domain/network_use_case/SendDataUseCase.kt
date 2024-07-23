package com.example.buzidroidapplication.domain.network_use_case

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
        bitmap: Bitmap
    ): Flow<MarkerSendResult> = flow {
        val byteArray = imageCompressor.compressImage(bitmap)
        val result = markerService.sendMarker(url = url, fileName = fileName, bodyByteArray = byteArray)

        when (result) {
            is MarkerService.Result.Success<*> -> { emit(MarkerSendResult.Success) }
            is MarkerService.Result.Error -> { emit(MarkerSendResult.Failed) }
        }
    }
}