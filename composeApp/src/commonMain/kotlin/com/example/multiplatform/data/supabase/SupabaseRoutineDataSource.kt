package com.example.multiplatform.data.supabase

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class SupabaseRoutineDataSource {

    private val routinesTable = SupabaseClientProvider.client.from("routines")
    private val exercisesTable = SupabaseClientProvider.client.from("routine_exercises")

    suspend fun getRoutineBySession(sessionId: String): RoutineDto? {
        return routinesTable
            .select { filter { eq("session_id", sessionId) } }
            .decodeList<RoutineDto>()
            .firstOrNull()
    }

    suspend fun createRoutine(sessionId: String, name: String): RoutineDto {
        return routinesTable
            .insert(NewRoutineDto(session_id = sessionId, name = name)) { select() }
            .decodeSingle<RoutineDto>()
    }

    suspend fun renameRoutine(routineId: String, name: String) {
        routinesTable
            .update({ set("name", name) }) { filter { eq("id", routineId) } }
    }

    suspend fun getExercises(routineId: String): List<RoutineExerciseDto> {
        return exercisesTable
            .select {
                filter { eq("routine_id", routineId) }
                order("position", Order.ASCENDING)
            }
            .decodeList<RoutineExerciseDto>()
    }

    suspend fun addExercise(
        routineId: String,
        exerciseId: String,
        exerciseName: String,
        muscleGroup: String,
        instructions: String,
        position: Int
    ) {
        exercisesTable.insert(
            NewRoutineExerciseDto(
                routine_id = routineId,
                exercise_id = exerciseId,
                exercise_name = exerciseName,
                muscle_group = muscleGroup,
                instructions = instructions,
                position = position
            )
        )
    }

    suspend fun removeExercise(routineId: String, exerciseId: String) {
        exercisesTable.delete {
            filter {
                eq("routine_id", routineId)
                eq("exercise_id", exerciseId)
            }
        }
    }

    suspend fun clearExercises(routineId: String) {
        exercisesTable.delete { filter { eq("routine_id", routineId) } }
    }
}
