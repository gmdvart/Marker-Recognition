package com.example.buzidroidapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarkerInfo(
    @SerialName("Name") val name: String,
    @SerialName("FullName") val fullName: String,
    @SerialName("FileName") val fileName: String,
    @SerialName("Code") val code: Int,
    @SerialName("TypeLoc") val typeLocation: Int
)
