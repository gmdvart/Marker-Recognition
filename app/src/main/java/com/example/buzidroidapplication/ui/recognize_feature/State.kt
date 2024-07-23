package com.example.buzidroidapplication.ui.recognize_feature

import com.example.buzidroidapplication.domain.model.MarkerUiModel

sealed interface State {
    data object Loading : State
    data class Ready(
        val markerList: List<MarkerUiModel>,
        val currentMarker: MarkerUiModel,
        // Properties which are used only in recognition purposes
        val isRecognizing: Boolean = false,
        val recognizedMarker: MarkerUiModel? = null
    ) : State
}