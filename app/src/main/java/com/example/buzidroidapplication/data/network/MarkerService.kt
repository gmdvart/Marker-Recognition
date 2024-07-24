package com.example.buzidroidapplication.data.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

interface MarkerService {
    suspend fun sendMarker(
        url: String,
        request: String = "apioodb",
        version: String = "1.0.0",
        apioodbType: String = "addfile",
        fileType: String = "filetype",
        fileName: String,
        bodyByteArray: ByteArray
    ): Result

    sealed class Result {
        data class Success<T>(val data: T? = null) : Result()
        data class Error(val message: String? = null) : Result()
    }
}

@Singleton
class DefaultMarkerService @Inject constructor(
    private val client: HttpClient
) : MarkerService {
    override suspend fun sendMarker(
        url: String,
        request: String,
        version: String,
        apioodbType: String,
        fileType: String,
        fileName: String,
        bodyByteArray: ByteArray
    ): MarkerService.Result {
        return try {
            client.post(url) {
                parameter(MarkerHttpParameters.REQUEST, request)
                parameter(MarkerHttpParameters.VERSION, version)
                parameter(MarkerHttpParameters.APIOODB_TYPE, apioodbType)
                parameter(MarkerHttpParameters.FILE_TYPE, fileType)
                parameter(MarkerHttpParameters.FILE_NAME, fileName)

                contentType(ContentType.Application.Json)
                setBody(bodyByteArray)
            }

            MarkerService.Result.Success<Nothing>()
        } catch (e: RedirectResponseException) {
            MarkerService.Result.Error(message = e.response.status.description)
        } catch (e: ClientRequestException) {
            MarkerService.Result.Error(message = e.response.status.description)
        } catch (e: ServerResponseException) {
            MarkerService.Result.Error(message = e.response.status.description)
        } catch (e: Exception) {
            MarkerService.Result.Error(message = e.message)
        }
    }
}