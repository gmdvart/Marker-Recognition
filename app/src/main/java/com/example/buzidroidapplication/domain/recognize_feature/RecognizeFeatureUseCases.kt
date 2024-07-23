package com.example.buzidroidapplication.domain.recognize_feature

import com.example.buzidroidapplication.data.repository.AppSettingsRepository
import com.example.buzidroidapplication.domain.markers_use_case.GetMarkerByIdUseCase
import com.example.buzidroidapplication.domain.markers_use_case.GetMarkersUseCase
import com.example.buzidroidapplication.domain.markers_use_case.GetRandomMarkerUseCase
import com.example.buzidroidapplication.domain.neural_use_case.GetPredictionIndexUseCase
import com.example.buzidroidapplication.domain.settings_use_case.GetAppSettingsUseCase
import javax.inject.Inject

data class RecognizeFeatureUseCases @Inject constructor(
    val getAppSettings: GetAppSettingsUseCase,
    val getMarkers: GetMarkersUseCase,
    val getMarkerById: GetMarkerByIdUseCase,
    val getRandomMarker: GetRandomMarkerUseCase,
    val getPredictionIndex: GetPredictionIndexUseCase
)
