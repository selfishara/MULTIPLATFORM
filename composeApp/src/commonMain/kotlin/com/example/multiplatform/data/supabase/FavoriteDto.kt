package com.example.multiplatform.data.supabase

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteDto(
    val id: String,
    val user_id: String,
    val exercise_id: String,
    val exercise_name: String,
    val muscle_group: String,
    val instructions: String
)

@Serializable
internal data class NewFavoriteDto(
    val user_id: String,
    val exercise_id: String,
    val exercise_name: String,
    val muscle_group: String,
    val instructions: String
)
