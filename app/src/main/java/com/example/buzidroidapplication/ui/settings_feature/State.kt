package com.example.buzidroidapplication.ui.settings_feature

sealed interface State {
    data object Loading : State
    data class Ready(
        val userName: String,
        val isLocalNetworkModeEnabled: Boolean,
        val isPredictionModeEnabled: Boolean
    ) : State
}