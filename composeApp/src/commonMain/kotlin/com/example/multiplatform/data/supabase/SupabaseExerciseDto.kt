package com.example.multiplatform.data.supabase

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseExerciseDto(
    val id: String,
    val name: String,
    val instructions: String,
    val language: String,
    val muscle_group: String,
    val source: String = "wger"
)