package com.example.buzidroidapplication.domain.settings_feature

import com.example.buzidroidapplication.domain.use_cases.settings.GetAppSettingsUseCase
import com.example.buzidroidapplication.domain.use_cases.settings.SwitchAppModeUseCase
import com.example.buzidroidapplication.domain.use_cases.settings.WriteUserNameUseCase
import javax.inject.Inject

data class SettingsFeatureUseCases @Inject constructor(
    val getAppSettings: GetAppSettingsUseCase,
    val switchAppMode: SwitchAppModeUseCase,
    val writeUserNameUseCase: WriteUserNameUseCase
)
