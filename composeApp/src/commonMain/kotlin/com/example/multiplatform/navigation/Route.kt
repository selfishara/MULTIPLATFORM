package com.example.multiplatform.navigation

/**
 * Defines the main navigation routes of the application.
 *
 * Each route represents a screen in the main flow of GymSpot Lite.
 */

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Route : NavKey {

    @Serializable
    data object Home : Route()

    @Serializable
    data object Exercises : Route()

    @Serializable
    data class ExerciseDetail(val exerciseId: String) : Route()

    @Serializable
    data object MyRoutine : Route()
}
        @Serializable
        data object Workout : Route()


