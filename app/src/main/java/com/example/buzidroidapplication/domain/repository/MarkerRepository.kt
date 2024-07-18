package com.example.buzidroidapplication.domain.repository

import com.example.buzidroidapplication.data.model.MarkerModel

interface MarkerRepository {
    fun getAllMarkers(): List<MarkerModel>

    fun getMarkerById(id: Int): MarkerModel?

    fun getRandomMarker(): MarkerModel
}