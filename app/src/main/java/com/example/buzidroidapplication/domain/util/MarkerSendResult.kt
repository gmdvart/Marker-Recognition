package com.example.buzidroidapplication.domain.util

interface MarkerSendResult {
    data object Success : MarkerSendResult
    data object Failed : MarkerSendResult
}