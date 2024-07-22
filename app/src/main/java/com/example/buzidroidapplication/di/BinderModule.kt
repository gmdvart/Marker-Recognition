package com.example.buzidroidapplication.di

import com.example.buzidroidapplication.data.network.DefaultMarkerService
import com.example.buzidroidapplication.data.network.MarkerService
import dagger.Binds
import dagger.Module

@Module
abstract class BinderModule {
    @Binds
    abstract fun bindDefaultMarkerServiceToMarkerService(defaultMarkerService: DefaultMarkerService): MarkerService
}