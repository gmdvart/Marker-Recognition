package com.example.buzidroidapplication.di

import com.example.buzidroidapplication.data.network.DefaultMarkerService
import com.example.buzidroidapplication.data.network.MarkerService
import dagger.Binds
import dagger.Module
import dagger.Reusable
import javax.inject.Qualifier

@Module
abstract class BinderModule {
    @Binds
    abstract fun bindDefaultMarkerServiceToMarkerService(defaultMarkerService: DefaultMarkerService): MarkerService
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DsMarkers

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NeedMarkers