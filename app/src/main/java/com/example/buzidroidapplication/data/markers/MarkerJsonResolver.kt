package com.example.buzidroidapplication.data.markers

import android.content.Context
import android.util.Log
import com.example.buzidroidapplication.data.model.MarkerInfo
import com.example.buzidroidapplication.data.model.MarkerModel
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarkerJsonResolver @Inject constructor(
    private val context: Context
) {
    fun resolveMarkers(path: Path): List<MarkerModel> {
        val jsonString = context.assets
            .open(path.path)
            .bufferedReader().use {
                it.readText()
            }

        val markersMap = Json.decodeFromString<Map<Int, MarkerInfo>>(jsonString)

        val markerModel = mutableListOf<MarkerModel>()
        for (markerEntry in markersMap.entries) {
            val info = markerEntry.value
            val drawable = getDrawableIdByName(info.fileName)
            if (drawable != 0) {
                markerModel.add(
                    MarkerModel(
                        id = markerEntry.key,
                        info = info,
                        drawableId = getDrawableIdByName(info.fileName)
                    )
                )
            }
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