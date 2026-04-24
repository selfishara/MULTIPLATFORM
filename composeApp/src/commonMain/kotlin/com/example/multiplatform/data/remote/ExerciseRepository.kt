package com.example.multiplatform.data.remote

import com.example.multiplatform.model.Exercise

interface ExerciseRepository {
    suspend fun getExercises(): List<Exercise>
}