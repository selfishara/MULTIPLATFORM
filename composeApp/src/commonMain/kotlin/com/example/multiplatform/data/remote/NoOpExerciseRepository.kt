package com.example.multiplatform.data.remote

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage

class NoOpExerciseRepository : ExerciseRepository {
    override suspend fun getExercises(preferredLanguage: ExerciseLanguage): List<Exercise> = emptyList()
}
