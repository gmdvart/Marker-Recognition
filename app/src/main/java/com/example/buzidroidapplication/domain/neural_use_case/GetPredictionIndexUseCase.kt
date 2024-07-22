package com.example.buzidroidapplication.domain.neural_use_case

import android.graphics.Bitmap
import com.example.buzidroidapplication.data.neural.ImagePreparer
import com.example.buzidroidapplication.data.neural.TensorFlowLiteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPredictionIndexUseCase @Inject constructor(
    private val tensorFlowLiteModel: TensorFlowLiteModel,
    private val imagePreparer: ImagePreparer
) {
    suspend operator fun invoke(bitmap: Bitmap, currentClassifierSize: Int): Flow<Int> = flow {
        val preparedData = imagePreparer.prepareImage(bitmap)
        val predictionIndex = tensorFlowLiteModel.getInference(preparedData, currentClassifierSize)
        emit(predictionIndex)
    }
}