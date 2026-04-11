package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.Exercise

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