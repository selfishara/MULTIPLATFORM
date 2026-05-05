package com.example.multiplatform.data.supabase

import com.example.multiplatform.model.Exercise
import io.github.jan.supabase.postgrest.from

class FavoriteDataSource {

    private val table = SupabaseClientProvider.client.from("favorites")

    suspend fun getFavoritesByUser(userId: String): List<FavoriteDto> {
        return table
            .select { filter { eq("user_id", userId) } }
            .decodeList()
    }

    suspend fun addFavorite(userId: String, exercise: Exercise) {
        table.insert(
            NewFavoriteDto(
                user_id = userId,
                exercise_id = exercise.id,
                exercise_name = exercise.name,
                muscle_group = exercise.muscleGroup.displayName,
                instructions = exercise.instructions
            )
        )
    }

    suspend fun removeFavorite(userId: String, exerciseId: String) {
        table.delete {
            filter {
                eq("user_id", userId)
                eq("exercise_id", exerciseId)
            }
        }
    }
}
