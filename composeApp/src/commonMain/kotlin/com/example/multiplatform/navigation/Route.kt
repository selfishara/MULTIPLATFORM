package com.example.multiplatform.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Route : NavKey {

    @Serializable
    data object Home : Route()

    @Serializable
    data object Exercises : Route()

    @Serializable
    data class ExerciseDetail(val exerciseId: String) : Route()
}