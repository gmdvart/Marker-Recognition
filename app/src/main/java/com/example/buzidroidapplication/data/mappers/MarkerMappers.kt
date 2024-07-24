package com.example.buzidroidapplication.data.mappers

import com.example.buzidroidapplication.data.model.MarkerModel
import com.example.buzidroidapplication.domain.model.MarkerUiModel

fun MarkerModel.toMakerUiModel(): MarkerUiModel =
    MarkerUiModel(
        id = id,
        drawableId = drawableId,
        fullName = info.fullName,
        fileName = info.fileName
    )