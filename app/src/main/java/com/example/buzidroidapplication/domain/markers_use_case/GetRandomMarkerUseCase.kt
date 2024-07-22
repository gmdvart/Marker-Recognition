package com.example.buzidroidapplication.domain.markers_use_case

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class GetRandomMarkerUseCase(
    private val dsMarkerRepository: MarkerRepository,
    private val needMarkerRepository: MarkerRepository
) {
    operator fun invoke(isPredictionMode: Boolean = false): MarkerUiModel {
        val randomMarker = if (isPredictionMode) dsMarkerRepository.getRandomMarker()
        else needMarkerRepository.getRandomMarker()

        return randomMarker.toMakerUiModel()
    }
}