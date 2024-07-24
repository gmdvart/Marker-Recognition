package com.example.buzidroidapplication.ui.settings_feature

sealed interface Action {
    data class SwitchAppMode(val checked: Boolean) : Action
    data class SwitchNetworkMode(val checked: Boolean) : Action
    data class EnterName(val newUserName: String) : Action
}