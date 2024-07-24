package com.example.buzidroidapplication.domain.use_cases.markers

import com.example.buzidroidapplication.data.mappers.toMakerUiModel
import com.example.buzidroidapplication.di.DsMarkers
import com.example.buzidroidapplication.di.NeedMarkers
import com.example.buzidroidapplication.domain.model.MarkerUiModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository
import javax.inject.Inject

class GetRandomMarkerUseCase @Inject constructor(
    @param:DsMarkers private val dsMarkerRepository: MarkerRepository,
    @param:NeedMarkers private val needMarkerRepository: MarkerRepository
) {
    operator fun invoke(isPredictionMode: Boolean = false): MarkerUiModel {
        val randomMarker = if (isPredictionMode) dsMarkerRepository.getRandomMarker()
        else needMarkerRepository.getRandomMarker()

        return randomMarker.toMakerUiModel()
    }
}