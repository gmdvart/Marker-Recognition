package com.example.buzidroidapplication.domain.markers_use_case

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.di.DsMarkers
import com.example.buzidroidapplication.di.NeedMarkers
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository
import javax.inject.Inject

class GetMarkerByIdUseCase @Inject constructor(
    @param:DsMarkers private val dsMarkerRepository: MarkerRepository,
    @param:NeedMarkers private val needMarkerRepository: MarkerRepository
) {
    operator fun invoke(id: Int, isPredictionMode: Boolean = false) : MarkerUiModel? {
        val marker = if (isPredictionMode) dsMarkerRepository.getMarkerById(id)
        else needMarkerRepository.getMarkerById(id)

        return marker?.toMakerUiModel()
    }
}