package com.example.buzidroidapplication.ui.settings_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.buzidroidapplication.domain.settings_feature.SettingsFeatureUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFeatureViewModel(
    private val settingsFeatureUseCases: SettingsFeatureUseCases
) : ViewModel() {
    private val appSettings = settingsFeatureUseCases.getAppSettings()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            appSettings.collectLatest { settings ->
                _state.update {
                    State.Ready(
                        settings.userName,
                        settings.predictionModeEnabled
                    )
                }
            }
        }
    }

    fun onAction(action: Action) {
        if (_state.value is State.Ready)
            viewModelScope.launch { performAction(action) }
    }

    private suspend fun performAction(action: Action) {
        when (action) {
            is Action.Switch -> {
                settingsFeatureUseCases.switchAppMode(action.checked)
            }
            is Action.EnterName -> {
                settingsFeatureUseCases.writeUserNameUseCase(action.newUserName)
            }
        }
    }

    class Factory @Inject constructor(
        private val settingsFeatureUseCases: SettingsFeatureUseCases
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsFeatureViewModel::class.java))
                return SettingsFeatureViewModel(settingsFeatureUseCases) as T
            else throw IllegalArgumentException("An unknown ViewModel class was provided")
        }
    }
}