package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.ExerciseLanguage

object AppSettingsState {
    var exerciseLanguage by mutableStateOf(ExerciseLanguage.ENGLISH)
        private set

    fun updateExerciseLanguage(language: ExerciseLanguage) {
        exerciseLanguage = language
    }
}