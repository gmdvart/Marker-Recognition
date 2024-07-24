package com.example.buzidroidapplication.ui.settings_feature

sealed interface Action {
    data class Switch(val checked: Boolean) : Action
    data class EnterName(val newUserName: String) : Action
}