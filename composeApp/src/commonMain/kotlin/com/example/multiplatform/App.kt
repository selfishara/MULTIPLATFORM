package com.example.multiplatform

import androidx.compose.runtime.Composable
import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.navigation.NavigationWrapper

@Composable
fun App(
    exerciseRepository: ExerciseRepository
) {
    NavigationWrapper(exerciseRepository = exerciseRepository)
}