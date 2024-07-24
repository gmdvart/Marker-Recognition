package com.example.buzidroidapplication.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(ContentNegotiation) {
            val jsonConfig = Json { ignoreUnknownKeys = true }
            json(jsonConfig)
        }
    }
}