package com.example.buzidroidapplication.domain.settings_use_case

import com.example.buzidroidapplication.data.repository.AppSettingsRepository
import javax.inject.Inject

class SwitchAppModeUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(isPredictionModeEnabled: Boolean) {
        appSettingsRepository.updateAppMode(isPredictionModeEnabled)
    }
}