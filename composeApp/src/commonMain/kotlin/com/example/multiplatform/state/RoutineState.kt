package com.example.multiplatform.state

/**
 * Simple state management for the user's workout routine (MVP - temporary solution).
 *
 * Manages a mutable list of exercises that the user has added to their routine,
 * as well as the routine's name. Provides methods to add, remove, and clear exercises,
 * and rename the routine. Prevents duplicate exercises from being added.
 *
 * Note: This is a temporary implementation for MVP. Will be replaced with ViewModel-based
 * state management in future versions.
 */
import com.example.multiplatform.model.Exercise

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object RoutineState {

    private val _routine = mutableStateListOf<Exercise>()
    var name by mutableStateOf("My Routine")
        private set

    val routine: List<Exercise>
        get() = _routine

    fun addExercise(exercise: Exercise) {
        if (_routine.none { it.id == exercise.id }) {
            _routine.add(exercise)
        }
    }

    fun removeExercise(exercise: Exercise) {
        _routine.removeAll { it.id == exercise.id }
    }

    fun clearRoutine() {
        _routine.clear()
    }

    fun renameRoutine(newName: String) {
        name = newName
    }
}