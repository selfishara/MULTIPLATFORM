package com.example.multiplatform.data.remote

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage

interface ExerciseRepository {
    suspend fun getExercises(
        preferredLanguage: ExerciseLanguage
    ): List<Exercise>
}