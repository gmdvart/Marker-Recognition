package com.example.buzidroidapplication.di

import dagger.Module

@Module(includes = [NetworkModule::class, SettingsModule::class, BinderModule::class])
interface AppModule