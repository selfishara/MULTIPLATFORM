package com.example.multiplatform.data.supabase

import io.github.jan.supabase.postgrest.from

class SupabaseExerciseDataSource {

    private val table = SupabaseClientProvider.client.from("exercises")

    suspend fun getExercisesByLanguage(language: String): List<SupabaseExerciseDto> {
        return table
            .select {
                filter {
                    eq("language", language)
                }
            }
            .decodeList<SupabaseExerciseDto>()
    }

    suspend fun existsByLanguage(language: String): Boolean {
        return try {
            table
                .select {
                    filter { eq("language", language) }
                    limit(1)
                }
                .decodeList<SupabaseExerciseDto>()
                .isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun upsertExercises(exercises: List<SupabaseExerciseDto>) {
        if (exercises.isEmpty()) return

        table.upsert(exercises)
    }
}