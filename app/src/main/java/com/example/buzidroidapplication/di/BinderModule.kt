package com.example.buzidroidapplication.di

import com.example.buzidroidapplication.data.network.DefaultMarkerService
import com.example.buzidroidapplication.data.network.MarkerService
import com.example.buzidroidapplication.data.repository.DsMarkerRepository
import com.example.buzidroidapplication.data.repository.NeedMarkerRepository
import com.example.buzidroidapplication.domain.repository.MarkerRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import javax.inject.Qualifier

@Module
abstract class BinderModule {
    @Binds
    abstract fun bindDefaultMarkerServiceToMarkerService(defaultMarkerService: DefaultMarkerService): MarkerService

    @Binds
    @DsMarkers
    abstract fun bindDsMarkerRepositoryToMarkerRepository(dsMarkerRepository: DsMarkerRepository): MarkerRepository

    @Binds
    @NeedMarkers
    abstract fun bindNeedMarkerRepositoryToMarkerRepository(needMarkerRepository: NeedMarkerRepository): MarkerRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DsMarkers

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NeedMarkers