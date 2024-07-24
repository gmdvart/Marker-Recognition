package com.example.buzidroidapplication.domain.use_cases.settings

import com.example.buzidroidapplication.data.repository.AppSettingsRepository
import javax.inject.Inject

class SwitchNetworkUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(localNetworkEnabled: Boolean) = appSettingsRepository.updateLocalNetworkMode(localNetworkEnabled)
}