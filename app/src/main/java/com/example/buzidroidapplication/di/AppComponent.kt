package com.example.buzidroidapplication.di

import android.content.Context
import com.example.buzidroidapplication.ui.recognize_feature.screen.MainScreenFragment
import com.example.buzidroidapplication.ui.recognize_feature.screen.MarkerListScreenFragment
import com.example.buzidroidapplication.ui.recognize_feature.screen.ResultDialogFragment
import com.example.buzidroidapplication.ui.settings_feature.UserNameDialogFragment
import com.example.buzidroidapplication.ui.settings_feature.screen.SettingsScreenFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainScreenFragment: MainScreenFragment)
    fun inject(markerListScreenFragment: MarkerListScreenFragment)
    fun inject(resultDialogFragment: ResultDialogFragment)
    fun inject(settingsScreenFragment: SettingsScreenFragment)
    fun inject(userNameDialogFragment: UserNameDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}