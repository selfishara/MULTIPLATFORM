package com.example.multiplatform.data.remote

import com.example.multiplatform.data.remote.dto.WgerExerciseDto
import com.example.multiplatform.data.remote.mapper.WgerMapper
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage
import kotlinx.coroutines.CancellationException

class WgerDataSource(
    private val api: WgerApi
) {
    private var cachedDtos: List<WgerExerciseDto> = emptyList()

    suspend fun getExercises(
        preferredLanguage: ExerciseLanguage
    ): List<Exercise> {
        return try {
            if (cachedDtos.isEmpty()) {
                cachedDtos = fetchInitialExerciseLibrary()
                println("TOTAL FETCHED INITIAL: ${cachedDtos.size}")
            }

            val filteredDtos = cachedDtos.filter { dto ->
                WgerMapper.hasCompleteTranslation(dto, preferredLanguage)
            }

            val mappedExercises = filteredDtos.map { dto ->
                WgerMapper.toDomain(dto, preferredLanguage)
            }

            println("Mapped exercises: ${mappedExercises.size} - language: $preferredLanguage")
            mappedExercises

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()

            cachedDtos
                .filter { dto ->
                    WgerMapper.hasCompleteTranslation(dto, preferredLanguage)
                }
                .map { dto ->
                    WgerMapper.toDomain(dto, preferredLanguage)
                }
        }
    }

    private suspend fun fetchInitialExerciseLibrary(): List<WgerExerciseDto> {
        val allExercises = mutableListOf<WgerExerciseDto>()

        val limit = 100
        val maxExercises = 200
        var offset = 0

        while (allExercises.size < maxExercises) {
            try {
                val response = api.getExercises(
                    limit = limit,
                    offset = offset
                )

                allExercises.addAll(response.results)

                println("Fetched chunk: ${response.results.size} | total: ${allExercises.size}")

                if (response.next == null || response.results.isEmpty()) break

                offset += limit

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
                println("Stopping pagination. Returning cached partial results: ${allExercises.size}")
                break
            }
        }

        return allExercises
    }
}