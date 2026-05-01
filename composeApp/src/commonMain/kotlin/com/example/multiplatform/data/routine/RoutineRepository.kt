package com.example.multiplatform.data.routine

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.Routine

interface RoutineRepository {
    suspend fun loadOrCreate(sessionId: String): Routine
    suspend fun addExercise(routineId: String, exercise: Exercise, position: Int)
    suspend fun removeExercise(routineId: String, exerciseId: String)
    suspend fun clearExercises(routineId: String)
    suspend fun rename(routineId: String, newName: String)
}
