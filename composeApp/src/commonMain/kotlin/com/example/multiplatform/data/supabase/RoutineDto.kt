package com.example.multiplatform.data.supabase

import kotlinx.serialization.Serializable

@Serializable
data class RoutineDto(
    val id: String,
    val session_id: String,
    val name: String
)

@Serializable
internal data class NewRoutineDto(
    val session_id: String,
    val name: String
)

@Serializable
data class RoutineExerciseDto(
    val id: String,
    val routine_id: String,
    val exercise_id: String,
    val exercise_name: String,
    val muscle_group: String,
    val instructions: String,
    val position: Int
)

@Serializable
internal data class NewRoutineExerciseDto(
    val routine_id: String,
    val exercise_id: String,
    val exercise_name: String,
    val muscle_group: String,
    val instructions: String,
    val position: Int
)
