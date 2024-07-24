package com.example.buzidroidapplication.domain.settings_feature

sealed interface State {
    data object Loading : State
    data class Ready(
        val userName: String,
        val isPredictionModeEnabled: Boolean
    ) : State
}