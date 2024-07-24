package com.example.buzidroidapplication.ui.recognize_feature

import android.graphics.Bitmap

sealed interface Action {
    data object SelectRandom : Action
    data class SelectById(val markerId: Int) : Action
    data class StartRecognition(val bitmap: Bitmap) : Action
    data object SendData : Action
}