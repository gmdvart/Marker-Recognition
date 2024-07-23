package com.example.buzidroidapplication.ui.recognize_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.buzidroidapplication.domain.recognize_feature.RecognizeFeatureUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject

class RecognizeFeatureViewModel(
    private val recognizeFeatureUseCases: RecognizeFeatureUseCases
) : ViewModel() {

    private val appSettings = recognizeFeatureUseCases.getAppSettings()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            appSettings.collectLatest { settings ->
                _state.update {
                    val markerList = recognizeFeatureUseCases.getMarkers(settings.predictionModeEnabled)
                    val randomMarker = recognizeFeatureUseCases.getRandomMarker()

                    State.Ready(markerList, randomMarker)
                }
            }
        }
    }

    fun onAction(action: Action) {
        _state.update { currentState ->
            when (currentState) {
                is State.Loading -> { currentState }
                is State.Ready -> { performAction(action, currentState) }
            }
        }
    }

    private fun performAction(
        action: Action,
        readyState: State.Ready,
    ): State.Ready = when (action) {
        is Action.SelectRandom -> {
            readyState.copy(currentMarker = recognizeFeatureUseCases.getRandomMarker())
        }
        is Action.SelectById -> {
            val marker = readyState.markerList.find { it.id == action.markerId }
            if (marker != null) readyState.copy(currentMarker = marker)
            else readyState
        }
        is Action.StartRecognition -> {
            viewModelScope.launch {
                _state.update { readyState.copy(isRecognizing = true) }

                delay(Duration.ofSeconds(1))
                recognizeFeatureUseCases
                    .getPredictionIndex(action.bitmap, readyState.markerList.size)
                    .collectLatest { index ->
                        _state.update {
                            val recognizedMarker = readyState.markerList[index]
                            readyState.copy(
                                isRecognizing = false,
                                recognizedMarker = recognizedMarker
                            )
                        }
                    }
            }
            readyState
        }
    }

    class Factory @Inject constructor(
        private val recognizeFeatureUseCases: RecognizeFeatureUseCases
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecognizeFeatureViewModel::class.java))
                return RecognizeFeatureViewModel(recognizeFeatureUseCases) as T
            else
                throw IllegalArgumentException("An unknown ViewModel class was provided.")
        }
    }
}