package com.example.multiplatform.state

import com.example.multiplatform.model.Exercise

object RoutineState {

    private val _routine = mutableListOf<Exercise>()
    private var _name: String = "My Routine"

    val routine: List<Exercise>
        get() = _routine

    val name: String
        get() = _name

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
        _name = newName
    }
}