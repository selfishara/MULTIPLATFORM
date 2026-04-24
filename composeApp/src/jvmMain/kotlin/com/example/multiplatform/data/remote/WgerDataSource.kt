package com.example.multiplatform.data.remote

import com.example.multiplatform.data.remote.mapper.WgerMapper
import com.example.multiplatform.model.Exercise

class WgerDataSource(
    private val api: WgerApi
) {
    suspend fun getExercises(): List<Exercise> {
        return try {
            val response = api.getExercises()
            response.results.map { dto ->
                WgerMapper.toDomain(dto)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}