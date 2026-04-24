package com.example.multiplatform.data.remote

import com.example.multiplatform.data.remote.dto.WgerResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class WgerApi(
    private val client: HttpClient
) {
    suspend fun getExercises(): WgerResponseDto {
        return client
            .get("https://wger.de/api/v2/exerciseinfo/")
            .body()
    }
}