package com.example.buzidroidapplication.di

import android.content.Context
import com.example.buzidroidapplication.ui.recognize_feature.screen.MainScreenFragment
import com.example.buzidroidapplication.ui.recognize_feature.screen.MarkerListScreenFragment
import com.example.buzidroidapplication.ui.recognize_feature.screen.ResultDialogFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainScreenFragment: MainScreenFragment)
    fun inject(markerListScreenFragment: MarkerListScreenFragment)
    fun inject(resultDialogFragment: ResultDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}