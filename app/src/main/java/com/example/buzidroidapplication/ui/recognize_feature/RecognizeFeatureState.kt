package com.example.buzidroidapplication.ui.recognize_feature

import com.example.buzidroidapplication.domain.model.MarkerUiModel

sealed interface RecognizeFeatureState {
    data object Loading : RecognizeFeatureState
    data class Ready(
        val markerList: List<MarkerUiModel>,
        val currentMarker: MarkerUiModel
    ) : RecognizeFeatureState
}