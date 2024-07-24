package com.example.buzidroidapplication.ui.recognize_feature

import android.graphics.Bitmap
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.util.MarkerSendResult

sealed interface State {
    data object Loading : State
    data class Ready(
        val isAbleToSendData: Boolean,
        val userName: String,
        val isLocalNetworkModeEnabled: Boolean,
        val markerList: List<MarkerUiModel>,
        val currentMarker: MarkerUiModel,
        val recognition: Recognition = Recognition.NotStarted,
        val sendResult: MarkerSendResult = MarkerSendResult.Idle
    ) : State

    sealed interface Recognition {
        data class Intent(
            val bitmap: Bitmap,
            val recognizedMarker: MarkerUiModel?,
            val recognizing: Boolean
        ) : Recognition
        data object NotStarted : Recognition
    }
}