package com.example.buzidroidapplication.domain.markers_use_case

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.di.DsMarkers
import com.example.buzidroidapplication.di.NeedMarkers
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository
import javax.inject.Inject

class GetMarkersUseCase @Inject constructor(
    @param:DsMarkers private val dsMarkerRepository: MarkerRepository,
    @param:NeedMarkers private val needMarkerRepository: MarkerRepository
) {
    operator fun invoke(isPredictionMode: Boolean = false): List<MarkerUiModel> {
        val markers = if (isPredictionMode) dsMarkerRepository.getAllMarkers()
        else needMarkerRepository.getAllMarkers()

        return markers.map { it.toMakerUiModel() }
    }
}