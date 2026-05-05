package com.example.multiplatform

import androidx.compose.runtime.Composable
import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.navigation.NavigationWrapper
import com.example.multiplatform.theme.GymSpotTheme

@Composable
fun App(
    exerciseSyncService: ExerciseSyncService,
    routineRepository: RoutineRepository
) {
    GymSpotTheme {
        NavigationWrapper(
            exerciseSyncService = exerciseSyncService,
            routineRepository = routineRepository
        )
    }
}
