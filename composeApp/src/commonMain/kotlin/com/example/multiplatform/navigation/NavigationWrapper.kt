package com.example.multiplatform.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.screens.CategoryExercisesScreen
import com.example.multiplatform.screens.ExerciseDetailScreen
import com.example.multiplatform.screens.ExercisesScreen
import com.example.multiplatform.screens.HomeScreen
import com.example.multiplatform.screens.MyRoutineScreen
import com.example.multiplatform.screens.WorkoutScreen
import com.example.multiplatform.state.RoutineState

@Composable
fun NavigationWrapper(
    exerciseRepository: ExerciseRepository
) {
    val backStack = rememberNavBackStack(navConfig, Route.Home)
    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }

    LaunchedEffect(Unit) {
        runCatching {
            exerciseRepository.getExercises()
        }.onSuccess { loadedExercises ->
            exercises = loadedExercises
        }.onFailure { error ->
            error.printStackTrace()
            exercises = emptyList()
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onStartClick = { backStack.add(Route.Exercises) },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) }
                )
            }

            entry<Route.Exercises> {
                ExercisesScreen(
                    onCategoryClick = { categoryName ->
                        backStack.add(Route.CategoryExercises(categoryName))
                    },
                    onBack = { backStack.removeLastOrNull() },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) }
                )
            }

            entry<Route.CategoryExercises> { key ->
                CategoryExercisesScreen(
                    categoryName = key.categoryName,
                    exercises = exercises,
                    onExerciseClick = { exerciseId ->
                        backStack.add(Route.ExerciseDetail(exerciseId))
                    },
                    onBack = { backStack.removeLastOrNull() },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) }
                )
            }

            entry<Route.ExerciseDetail> { key ->
                ExerciseDetailScreen(
                    exerciseId = key.exerciseId,
                    exercises = exercises,
                    onAddToRoutine = { exercise ->
                        RoutineState.addExercise(exercise)
                    },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }

            entry<Route.MyRoutine> {
                MyRoutineScreen(
                    onBack = { backStack.removeLastOrNull() },
                    onStartWorkout = { backStack.add(Route.Workout) }
                )
            }

            entry<Route.Workout> {
                WorkoutScreen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}