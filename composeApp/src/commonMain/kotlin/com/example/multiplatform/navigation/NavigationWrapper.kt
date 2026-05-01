package com.example.multiplatform.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.data.sync.SyncState
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.platform.SessionProvider
import com.example.multiplatform.screens.CategoryExercisesScreen
import com.example.multiplatform.screens.ExerciseDetailScreen
import com.example.multiplatform.screens.ExercisesScreen
import com.example.multiplatform.screens.HomeScreen
import com.example.multiplatform.screens.MyRoutineScreen
import com.example.multiplatform.screens.WorkoutScreen
import com.example.multiplatform.state.AppSettingsState
import com.example.multiplatform.state.RoutineState
import kotlinx.coroutines.CancellationException

@Composable
fun NavigationWrapper(
    exerciseSyncService: ExerciseSyncService,
    routineRepository: RoutineRepository
) {
    val backStack = rememberNavBackStack(navConfig, Route.Home)
    val scope = rememberCoroutineScope()

    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    val syncState by exerciseSyncService.syncState.collectAsState()
    val selectedLanguage = AppSettingsState.exerciseLanguage

    remember { RoutineState.init(routineRepository, scope) }

    LaunchedEffect(Unit) {
        RoutineState.markLoading(true)
        try {
            val routine = routineRepository.loadOrCreate(SessionProvider.getSessionId())
            RoutineState.loadFromRemote(routine.id, routine.name, routine.exercises)
            println("[Routine] Loaded '${routine.name}' with ${routine.exercises.size} exercises")
        } catch (e: Exception) {
            RoutineState.markLoading(false)
            println("[Routine] Failed to load: ${e.message}")
        }
    }

    val isLoading = syncState is SyncState.Loading
    val exerciseCount = when (val s = syncState) {
        is SyncState.Success -> s.count
        is SyncState.Error -> if (s.hasFallback) s.fallbackCount else exercises.size
        else -> exercises.size
    }
    val syncError = (syncState as? SyncState.Error)
        ?.takeIf { !it.hasFallback }
        ?.message

    LaunchedEffect(selectedLanguage) {
        runCatching {
            exerciseSyncService.getExercises(selectedLanguage)
        }.onSuccess { loadedExercises ->
            if (loadedExercises.isNotEmpty()) {
                exercises = loadedExercises
            }
        }.onFailure { error ->
            if (error is CancellationException) return@onFailure
            error.printStackTrace()
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onStartClick = { backStack.add(Route.Exercises) },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) },
                    selectedLanguage = AppSettingsState.exerciseLanguage,
                    onLanguageChange = { language ->
                        AppSettingsState.updateExerciseLanguage(language)
                    },
                    isLoading = isLoading,
                    exerciseCount = exerciseCount,
                    syncError = syncError
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
                    isLoading = isLoading,
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
