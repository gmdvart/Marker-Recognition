package com.example.buzidroidapplication.data.repository

import com.example.buzidroidapplication.data.markers.MarkerJsonResolver
import com.example.buzidroidapplication.data.model.MarkerModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class NeedMarkerRepository(
    markerJsonResolver: MarkerJsonResolver
): MarkerRepository {
    private val needMarkers = markerJsonResolver.resolveMarkers(
        path = MarkerJsonResolver.Path.NeedMarkersClassifier()
    )

    override fun getAllMarkers(): List<MarkerModel> {
        return needMarkers
    }

    override fun getMarkerById(id: Int): MarkerModel? {
        return needMarkers.find { it.id == id }
    }

    override fun getRandomMarker(): MarkerModel {
        return needMarkers.random()
    }
}