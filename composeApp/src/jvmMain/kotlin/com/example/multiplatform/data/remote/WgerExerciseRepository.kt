package com.example.multiplatform.data

import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.data.remote.WgerDataSource
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage

class WgerExerciseRepository(
    private val dataSource: WgerDataSource
) : ExerciseRepository {

    override suspend fun getExercises(
        preferredLanguage: ExerciseLanguage
    ): List<Exercise> {
        return dataSource.getExercises(preferredLanguage)
    }
}