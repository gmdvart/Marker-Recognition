package com.example.buzidroidapplication.domain.util

interface MarkerSendResult {
    data object Idle : MarkerSendResult
    data object Success : MarkerSendResult
    data class Failed(val cause: String) : MarkerSendResult
}