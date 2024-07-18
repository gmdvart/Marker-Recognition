package com.example.buzidroidapplication.data.repository

import com.example.buzidroidapplication.data.markers.MarkerJsonResolver
import com.example.buzidroidapplication.data.model.MarkerModel
import com.example.buzidroidapplication.domain.repository.MarkerRepository

class DefaultMarkerRepository(
    markerJsonResolver: MarkerJsonResolver
): MarkerRepository {
    private val markers = markerJsonResolver.resolveMarkers(
        path = MarkerJsonResolver.Path.OtoMarkersData()
    )

    override fun getAllMarkers(): List<MarkerModel> {
        return markers
    }

    override fun getMarkerById(id: Int): MarkerModel? {
        return markers.find { it.id == id }
    }

    override fun getRandomMarker(): MarkerModel {
        return markers.random()
    }
}