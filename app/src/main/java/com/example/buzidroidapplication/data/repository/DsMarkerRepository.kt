package com.example.buzidroidapplication.data.repository

import com.example.buzidroidapplication.data.markers.MarkerJsonResolver
import com.example.buzidroidapplication.data.model.MarkerModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class DsMarkerRepository(
    markerJsonResolver: MarkerJsonResolver
) : MarkerRepository {
    private val dsMarkers = markerJsonResolver.resolveMarkers(
        path = MarkerJsonResolver.Path.DsClassifier()
    )

    override fun getAllMarkers(): List<MarkerModel> {
        return dsMarkers
    }

    override fun getMarkerById(id: Int): MarkerModel? {
        return dsMarkers.find { it.id == id }
    }

    override fun getRandomMarker(): MarkerModel {
        return dsMarkers.random()
    }
}