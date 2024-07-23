package com.example.buzidroidapplication.data.model

import androidx.annotation.DrawableRes

data class MarkerModel(
    val id: Int,
    val info: MarkerInfo,
    @DrawableRes val drawableId: Int
)
