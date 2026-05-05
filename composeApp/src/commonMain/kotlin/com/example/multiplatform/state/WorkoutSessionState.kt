package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.Exercise

object WorkoutSessionState {

    const val TARGET_SETS = 3
    const val TARGET_REPS = 10
    const val REST_SECONDS = 60

    private val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises

    var currentIndex by mutableStateOf(0)
        private set
    var setsCompleted by mutableStateOf(0)
        private set
    var totalSetsCompleted by mutableStateOf(0)
        private set
    var totalSeconds by mutableStateOf(0)
        private set
    var restSecondsLeft by mutableStateOf(-1)
        private set
    var isFinished by mutableStateOf(false)
        private set

    val currentExercise get() = _exercises.getOrNull(currentIndex)
    val isResting get() = restSecondsLeft >= 0
    val allSetsForCurrentDone get() = setsCompleted >= TARGET_SETS
    val progress get() = if (_exercises.isEmpty()) 0f else (currentIndex + 1f) / _exercises.size

    fun start(routine: List<Exercise>) {
        _exercises.clear()
        _exercises.addAll(routine)
        currentIndex = 0
        setsCompleted = 0
        totalSetsCompleted = 0
        totalSeconds = 0
        restSecondsLeft = -1
        isFinished = false
    }

    fun tickSecond() {
        if (isFinished) return
        totalSeconds++
        when {
            restSecondsLeft > 0 -> restSecondsLeft--
            restSecondsLeft == 0 -> restSecondsLeft = -1
        }
    }

    fun completeSet() {
        if (isResting || allSetsForCurrentDone) return
        setsCompleted++
        totalSetsCompleted++
        if (setsCompleted < TARGET_SETS) {
            restSecondsLeft = REST_SECONDS
        }
    }

    fun skipRest() {
        restSecondsLeft = -1
    }

    fun nextExercise() {
        if (currentIndex < _exercises.lastIndex) {
            currentIndex++
            setsCompleted = 0
            restSecondsLeft = -1
        } else {
            isFinished = true
        }
    }
}
