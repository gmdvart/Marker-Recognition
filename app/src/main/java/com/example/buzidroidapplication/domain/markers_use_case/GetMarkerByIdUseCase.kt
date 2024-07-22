package com.example.buzidroidapplication.domain.markers_use_case

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class GetMarkerByIdUseCase(
    private val dsMarkerRepository: MarkerRepository,
    private val needMarkerRepository: MarkerRepository
) {
    operator fun invoke(id: Int, isPredictionMode: Boolean = false) : MarkerUiModel? {
        val marker = if (isPredictionMode) dsMarkerRepository.getMarkerById(id)
        else needMarkerRepository.getMarkerById(id)

        return marker?.toMakerUiModel()
    }
}