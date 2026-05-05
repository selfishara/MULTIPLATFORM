package com.example.multiplatform.data.supabase

import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup
import com.example.multiplatform.model.Routine

class SupabaseRoutineRepository(
    private val dataSource: SupabaseRoutineDataSource
) : RoutineRepository {

    override suspend fun loadOrCreate(sessionId: String): Routine {
        val dto = dataSource.getRoutineBySession(sessionId)
            ?: dataSource.createRoutine(sessionId, "My Routine")

        val exercises = dataSource.getExercises(dto.id).map { ex ->
            Exercise(
                id = ex.exercise_id,
                name = ex.exercise_name,
                muscleGroup = MuscleGroup.fromString(ex.muscle_group),
                instructions = ex.instructions
            )
        }

        return Routine(id = dto.id, name = dto.name, exercises = exercises)
    }

    override suspend fun addExercise(routineId: String, exercise: Exercise, position: Int) {
        dataSource.addExercise(
            routineId = routineId,
            exerciseId = exercise.id,
            exerciseName = exercise.name,
            muscleGroup = exercise.muscleGroup.displayName,
            instructions = exercise.instructions,
            position = position
        )
    }

    override suspend fun removeExercise(routineId: String, exerciseId: String) {
        dataSource.removeExercise(routineId, exerciseId)
    }

    override suspend fun clearExercises(routineId: String) {
        dataSource.clearExercises(routineId)
    }

    override suspend fun rename(routineId: String, newName: String) {
        dataSource.renameRoutine(routineId, newName)
    }
}
