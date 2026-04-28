package com.example.multiplatform.data.remote

import com.example.multiplatform.data.remote.dto.WgerResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WgerApi(
    private val client: HttpClient
) {
    suspend fun getExercises(
        limit: Int = 20,
        offset: Int = 0
    ): WgerResponseDto {
        return client
            .get("https://wger.de/api/v2/exerciseinfo/"){
                parameter("limit", limit)
                parameter("offset", offset)
            }
            .body()
    }
}