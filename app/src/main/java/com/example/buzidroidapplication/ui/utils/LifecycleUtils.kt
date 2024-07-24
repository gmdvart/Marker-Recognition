package com.example.buzidroidapplication.ui.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectLatestState(flow: Flow<T>, callback: (T) -> Unit) {
    lifecycleScope.launch {
        flow.collectLatest {
            callback(it)
        }
    }
}