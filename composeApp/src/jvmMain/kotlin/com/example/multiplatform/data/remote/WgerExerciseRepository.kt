package com.example.multiplatform.data

import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.data.remote.WgerDataSource
import com.example.multiplatform.model.Exercise

class WgerExerciseRepository(
    private val dataSource: WgerDataSource
) : ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        return dataSource.getExercises()
    }
}