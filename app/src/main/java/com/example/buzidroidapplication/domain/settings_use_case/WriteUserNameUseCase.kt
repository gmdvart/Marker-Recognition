package com.example.buzidroidapplication.domain.settings_use_case

import com.example.buzidroidapplication.data.repository.AppSettingsRepository

class WriteUserNameUseCase(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(userName: String) {
        appSettingsRepository.updateUserName(userName)
    }
}