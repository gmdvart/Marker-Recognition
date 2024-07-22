package com.example.buzidroidapplication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val APP_SETTINGS = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_SETTINGS)

class BuziDroidApplication : Application()