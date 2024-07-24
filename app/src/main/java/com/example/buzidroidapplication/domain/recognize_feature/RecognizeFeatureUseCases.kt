package com.example.buzidroidapplication.domain.recognize_feature

import com.example.buzidroidapplication.domain.use_cases.markers.GetMarkerByIdUseCase
import com.example.buzidroidapplication.domain.use_cases.markers.GetMarkersUseCase
import com.example.buzidroidapplication.domain.use_cases.markers.GetRandomMarkerUseCase
import com.example.buzidroidapplication.domain.use_cases.network.SendDataUseCase
import com.example.buzidroidapplication.domain.use_cases.neural.GetPredictionIndexUseCase
import com.example.buzidroidapplication.domain.use_cases.settings.GetAppSettingsUseCase
import javax.inject.Inject

data class RecognizeFeatureUseCases @Inject constructor(
    val getAppSettings: GetAppSettingsUseCase,
    val getMarkers: GetMarkersUseCase,
    val getMarkerById: GetMarkerByIdUseCase,
    val getRandomMarker: GetRandomMarkerUseCase,
    val getPredictionIndex: GetPredictionIndexUseCase,
    val sendData: SendDataUseCase
)
