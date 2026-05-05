package com.example.multiplatform.data.supabase

import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage

class SupabaseExerciseRepository(
    private val dataSource: SupabaseExerciseDataSource
) : ExerciseRepository {

    override suspend fun getExercises(
        preferredLanguage: ExerciseLanguage
    ): List<Exercise> {

        val languageCode = preferredLanguage.apiCode.lowercase()

        val exercises = dataSource
            .getExercisesByLanguage(languageCode)
            .map {
                SupabaseExerciseMapper.toDomain(it)
            }

        println("Loaded from Supabase: ${exercises.size} - language: $languageCode")

        return exercises
    }

    suspend fun existsByLanguage(language: ExerciseLanguage): Boolean {
        return dataSource.existsByLanguage(language.apiCode.lowercase())
    }

    suspend fun cacheExercises(
        exercises: List<Exercise>,
        language: ExerciseLanguage
    ) {
        val dtos = exercises.map { exercise ->
            SupabaseExerciseMapper.fromDomain(
                exercise = exercise,
                language = language.apiCode.lowercase()
            )
        }

        dataSource.upsertExercises(dtos)

        println("Cached in Supabase: ${dtos.size} - language: ${language.apiCode}")
    }
}