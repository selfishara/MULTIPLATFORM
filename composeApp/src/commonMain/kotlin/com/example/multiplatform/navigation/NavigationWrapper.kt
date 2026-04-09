package com.example.multiplatform.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

import com.example.multiplatform.screens.ExerciseDetailScreen
import com.example.multiplatform.screens.ExercisesScreen
import com.example.multiplatform.screens.HomeScreen

@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack(navConfig, Route.Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onStartClick = {
                        backStack.add(Route.Exercises)
                    }
                )
            }

            entry<Route.Exercises> {
                ExercisesScreen(
                    onExerciseClick = { exerciseId ->
                        backStack.add(Route.ExerciseDetail(exerciseId))
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Route.ExerciseDetail> { key ->
                ExerciseDetailScreen(
                    exerciseId = key.exerciseId,
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}