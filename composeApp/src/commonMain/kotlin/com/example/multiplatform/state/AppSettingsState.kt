package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.ExerciseLanguage

object AppSettingsState {
    var exerciseLanguage by mutableStateOf(ExerciseLanguage.ENGLISH)
        private set
    var targetSets by mutableStateOf(3)
        private set
    var targetReps by mutableStateOf(10)
        private set
    var restSeconds by mutableStateOf(60)
        private set

    fun updateExerciseLanguage(language: ExerciseLanguage) {
        exerciseLanguage = language
    }

    fun updateTargetSets(value: Int) {
        targetSets = value.coerceIn(1, 5)
    }

    fun updateTargetReps(value: Int) {
        targetReps = value.coerceIn(5, 20)
    }

    fun updateRestSeconds(value: Int) {
        restSeconds = value.coerceIn(15, 120)
    }
}