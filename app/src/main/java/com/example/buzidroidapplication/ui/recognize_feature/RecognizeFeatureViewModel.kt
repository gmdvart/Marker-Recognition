package com.example.buzidroidapplication.ui.recognize_feature

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.buzidroidapplication.data.network.MarkerHttpRoutes
import com.example.buzidroidapplication.domain.recognize_feature.RecognizeFeatureUseCases
import com.example.buzidroidapplication.domain.util.MarkerSendResult
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

                    State.Ready(
                        !settings.predictionModeEnabled, // We can only send data when prediction mode is disabled
                        settings.userName,
                        settings.localNetworkEnabled,
                        markerList,
                        randomMarker
                    )
                }
            }
        }
    }

    fun onAction(action: Action) {
        _state.update { currentState ->
            when (currentState) {
                is State.Loading -> {
                    currentState
                }

                is State.Ready -> {
                    performAction(action, currentState)
                }
            }
        }
    }

    private fun performAction(
        action: Action,
        readyState: State.Ready,
    ): State.Ready = when (action) {
        is Action.SelectRandom -> {
            performSelectRandomMarkerAction(readyState)
        }

        is Action.SelectById -> {
            performSelectionByIdAction(readyState, action.markerId)
        }

        is Action.StartRecognition -> {
            performStartRecognitionAction(readyState, action.bitmap)
        }

        is Action.SendData -> {
            performSendDataAction(readyState)
        }
    }

    private fun performSelectRandomMarkerAction(readyState: State.Ready): State.Ready {
        return readyState.copy(currentMarker = recognizeFeatureUseCases.getRandomMarker())
    }

    private fun performSelectionByIdAction(readyState: State.Ready, markerId: Int): State.Ready {
        val marker = readyState.markerList.find { it.id == markerId }
        return if (marker != null) readyState.copy(currentMarker = marker)
        else readyState
    }

    private fun performStartRecognitionAction(readyState: State.Ready, bitmap: Bitmap): State.Ready {
        _state.update {
            readyState.copy(
                recognition = State.Recognition.Intent(
                    recognizing = true,
                    bitmap = bitmap,
                    recognizedMarker = null
                )
            )
        }

        viewModelScope.launch {
            val dataSetMarkers = recognizeFeatureUseCases.getMarkers(true)
            delay(Duration.ofSeconds(1))

            recognizeFeatureUseCases
                .getPredictionIndex(bitmap, dataSetMarkers.size)
                .collectLatest { index ->
                    _state.update {
                        val recognizedMarker = dataSetMarkers[index]

                        readyState.copy(
                            recognition = State.Recognition.Intent(
                                recognizing = false,
                                bitmap = bitmap,
                                recognizedMarker = recognizedMarker
                            )
                        )
                    }
                }
        }
        return readyState
    }

    private fun performSendDataAction(readyState: State.Ready): State.Ready {
        Log.d("${RecognizeFeatureViewModel::class.simpleName}", "Click")
        viewModelScope.launch {
            if (readyState.recognition is State.Recognition.Intent) {
                val recognizedMarker = readyState.recognition.recognizedMarker

                recognizedMarker?.let {
                    recognizeFeatureUseCases
                        .sendData(
                            url = if (readyState.isLocalNetworkModeEnabled) MarkerHttpRoutes.LOCAL_URL else MarkerHttpRoutes.GLOBAL_URL,
                            fileName = readyState.currentMarker.fileName,
                            userName = readyState.userName,
                            bitmap = readyState.recognition.bitmap,
                            isRecognitionCorrect = it.id == readyState.currentMarker.id
                        ).collectLatest { result ->
                            _state.update {
                                readyState.copy(sendResult = result)
                            }
                        }
                }
            }
        }
        return readyState
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