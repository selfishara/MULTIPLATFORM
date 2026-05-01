package com.example.multiplatform

import androidx.compose.runtime.Composable
import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.navigation.NavigationWrapper

@Composable
fun App(
    exerciseSyncService: ExerciseSyncService,
    routineRepository: RoutineRepository
) {
    NavigationWrapper(
        exerciseSyncService = exerciseSyncService,
        routineRepository = routineRepository
    )
}
