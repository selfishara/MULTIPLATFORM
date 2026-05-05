package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.Exercise

object WorkoutSessionState {

    private val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises

    var targetSets by mutableStateOf(3)
        private set
    var targetReps by mutableStateOf(10)
        private set
    var restSeconds by mutableStateOf(60)
        private set

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
    val allSetsForCurrentDone get() = setsCompleted >= targetSets
    val progress get() = if (_exercises.isEmpty()) 0f else (currentIndex + 1f) / _exercises.size

    fun start(routine: List<Exercise>) {
        _exercises.clear()
        _exercises.addAll(routine)
        targetSets = AppSettingsState.targetSets
        targetReps = AppSettingsState.targetReps
        restSeconds = AppSettingsState.restSeconds
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
        if (setsCompleted < targetSets) {
            restSecondsLeft = restSeconds
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
