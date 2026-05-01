package com.example.multiplatform

import androidx.compose.runtime.Composable
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.navigation.NavigationWrapper

@Composable
fun App(
    exerciseSyncService: ExerciseSyncService
) {
    NavigationWrapper(
        exerciseSyncService = exerciseSyncService
    )
}
