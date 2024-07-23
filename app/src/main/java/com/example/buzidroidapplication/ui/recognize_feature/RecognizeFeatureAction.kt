package com.example.buzidroidapplication.ui.recognize_feature

sealed interface RecognizeFeatureAction {
    data object SelectRandom : RecognizeFeatureAction
    data class SelectById(val markerId: Int) : RecognizeFeatureAction
}