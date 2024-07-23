package com.example.buzidroidapplication.ui.recognize_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.buzidroidapplication.data.repository.AppSettingsRepository
import com.example.buzidroidapplication.domain.recognize_feature.RecognizeFeatureUseCases
import com.example.buzidroidapplication.ui.recognize_feature.screen.MarkerListScreenFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecognizeFeatureViewModel(
    private val recognizeFeatureUseCases: RecognizeFeatureUseCases
) : ViewModel() {

    private val appSettings = recognizeFeatureUseCases.getAppSettings()

    private val _state = MutableStateFlow<RecognizeFeatureState>(RecognizeFeatureState.Loading)
    val state: StateFlow<RecognizeFeatureState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            appSettings.collectLatest { settings ->
                _state.update {
                    val markerList = recognizeFeatureUseCases.getMarkers(settings.predictionModeEnabled)
                    val randomMarker = recognizeFeatureUseCases.getRandomMarker()

                    RecognizeFeatureState.Ready(markerList, randomMarker)
                }
            }
        }
    }

    fun onAction(action: RecognizeFeatureAction) {
        _state.update { currentState ->
            when (currentState) {
                is RecognizeFeatureState.Loading -> { currentState }
                is RecognizeFeatureState.Ready -> { performAction(action, currentState) }
            }
        }
    }

    private fun performAction(
        action: RecognizeFeatureAction,
        readyState: RecognizeFeatureState.Ready,
    ): RecognizeFeatureState.Ready = when (action) {
        is RecognizeFeatureAction.SelectRandom -> {
            readyState.copy(currentMarker = recognizeFeatureUseCases.getRandomMarker())
        }
        is RecognizeFeatureAction.SelectById -> {
            val marker = readyState.markerList.find { it.id == action.markerId }
            if (marker != null) readyState.copy(currentMarker = marker)
            else readyState
        }
    }

    class Factory @Inject constructor(private val recognizeFeatureUseCases: RecognizeFeatureUseCases) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecognizeFeatureViewModel::class.java))
                return RecognizeFeatureViewModel(recognizeFeatureUseCases) as T
            else
                throw IllegalArgumentException("An unknown ViewModel class was provided.")
        }
    }
}