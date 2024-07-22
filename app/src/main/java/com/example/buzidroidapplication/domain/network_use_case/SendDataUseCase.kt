package com.example.buzidroidapplication.domain.network_use_case

import com.example.buzidroidapplication.data.network.MarkerService
import com.example.buzidroidapplication.domain.util.MarkerSendResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendDataUseCase @Inject constructor(
    private val markerService: MarkerService
) {
    suspend operator fun invoke(
        url: String,
        fileName: String,
        bodyByteArray: ByteArray
    ): Flow<MarkerSendResult> = flow {
        val result = markerService.sendMarker(url = url, fileName = fileName, bodyByteArray = bodyByteArray)

        when (result) {
            is MarkerService.Result.Success<*> -> { emit(MarkerSendResult.Success) }
            is MarkerService.Result.Error -> { emit(MarkerSendResult.Failed) }
        }
    }
}