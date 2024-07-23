package com.example.buzidroidapplication.domain.settings_use_case

import com.example.buzidroidapplication.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    operator fun invoke(): Flow<AppSettingsRepository.AppSettings> = appSettingsRepository.appSettings
}