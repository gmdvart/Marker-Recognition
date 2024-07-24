package com.example.buzidroidapplication.domain.settings_feature

sealed interface Action {
    data class Switch(val checked: Boolean) : Action
    data class EnterName(val newUserName: String) : Action
}