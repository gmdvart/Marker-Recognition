package com.example.buzidroidapplication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.buzidroidapplication.di.AppComponent
import com.example.buzidroidapplication.di.DaggerAppComponent

const val APP_SETTINGS = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_SETTINGS)

class BuziDroidApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        super.onCreate()
    }

}

val Context.appComponent: AppComponent
    get() = when (val context = this) {
        is BuziDroidApplication -> { context.appComponent }
        else -> { (context.applicationContext as BuziDroidApplication).appComponent }
    }
