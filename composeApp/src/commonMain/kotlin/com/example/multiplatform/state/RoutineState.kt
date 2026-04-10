package com.example.multiplatform.state

/**
 * Simple state management for the user's workout routine (MVP - temporary solution).
 *
 * Manages a mutable list of exercises that the user has added to their routine.
 * Provides methods to add and remove exercises, preventing duplicates.
 *
 * Note: This is a temporary implementation for MVP. Will be replaced with ViewModel-based
 * state management in future versions.
 */
import com.example.multiplatform.model.Exercise

object RoutineState {
    private val _routine = mutableListOf<Exercise>()

    val routine: List<Exercise>
        get() = _routine

    fun addExercise(exercise: Exercise) {
        if (!_routine.any { it.id == exercise.id }) {
            _routine.add(exercise)
        }
    }

    fun removeExercise(exercise: Exercise) {
        _routine.removeAll { it.id == exercise.id }
    }

    fun clearRoutine() {
        _routine.clear()
    }
}