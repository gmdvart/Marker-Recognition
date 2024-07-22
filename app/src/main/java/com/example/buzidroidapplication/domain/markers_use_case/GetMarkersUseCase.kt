package com.example.buzidroidapplication.domain.markers_use_case

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class GetMarkersUseCase(
    private val dsMarkerRepository: MarkerRepository,
    private val needMarkerRepository: MarkerRepository
) {
    fun invoke(isPredictionMode: Boolean = false): List<MarkerUiModel> {
        val markers = if (isPredictionMode) dsMarkerRepository.getAllMarkers()
        else needMarkerRepository.getAllMarkers()

        return markers.map { it.toMakerUiModel() }
    }
}