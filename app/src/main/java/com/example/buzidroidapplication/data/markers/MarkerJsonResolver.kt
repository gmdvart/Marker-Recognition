package com.example.buzidroidapplication.data.markers

import android.content.Context
import com.example.buzidroidapplication.data.model.MarkerInfo
import com.example.buzidroidapplication.data.model.MarkerModel
import kotlinx.serialization.json.Json

class MarkerJsonResolver(
    private val context: Context
) {
    fun resolveMarkers(path: Path): List<MarkerModel> {
        val jsonString = context.assets
            .open(path.path)
            .bufferedReader().use {
                it.readText()
            }

        val markersMap = Json.decodeFromString<Map<Int, MarkerInfo>>(jsonString)

        val markerModel = markersMap.map { markerEntry ->
            val info = markerEntry.value
            MarkerModel(
                id = markerEntry.key,
                info = info,
                drawableId = getDrawableIdByName(info.fileName)
            )
        }

        return markerModel
    }

    private fun getDrawableIdByName(fileName: String) =
        context.resources
            .getIdentifier(
                fileName,
                "drawable",
                context.packageName
            )

    sealed class Path(val path: String) {
        class NeedMarkersClassifier : Path("needMarkersClassificator.json")
        class DsClassifier : Path("dsClassificator.json")
    }
}