package com.example.multiplatform.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.multiplatform.data.PredefinedRoutines
import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.data.supabase.FavoriteDataSource
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.data.sync.SyncState
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup
import com.example.multiplatform.screens.CategoryExercisesScreen
import com.example.multiplatform.screens.ExerciseDetailScreen
import com.example.multiplatform.screens.ExercisesScreen
import com.example.multiplatform.screens.FavoritesScreen
import com.example.multiplatform.screens.HistoryScreen
import com.example.multiplatform.screens.HomeScreen
import com.example.multiplatform.screens.LoginScreen
import com.example.multiplatform.screens.MyRoutineScreen
import com.example.multiplatform.screens.ProfileScreen
import com.example.multiplatform.screens.SettingsScreen
import com.example.multiplatform.screens.WorkoutScreen
import com.example.multiplatform.state.AppSettingsState
import com.example.multiplatform.state.AuthState
import com.example.multiplatform.state.FavoritesState
import com.example.multiplatform.state.RoutineState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper(
    exerciseSyncService: ExerciseSyncService,
    routineRepository: RoutineRepository
) {
    LaunchedEffect(Unit) {
        AuthState.init()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (!AuthState.isChecking) {
            MainContent(exerciseSyncService, routineRepository)
        }
    }
}

@Composable
private fun MainContent(
    exerciseSyncService: ExerciseSyncService,
    routineRepository: RoutineRepository
) {
    val scope = rememberCoroutineScope()
    val favoriteDataSource = remember { FavoriteDataSource() }

    remember { RoutineState.init(routineRepository, scope) }
    remember { FavoritesState.init(favoriteDataSource, scope) }

    val initialRoute: Route = if (AuthState.isLoggedIn) Route.Home else Route.Login
    val backStack = rememberNavBackStack(navConfig, initialRoute)

    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    val syncState by exerciseSyncService.syncState.collectAsState()
    val selectedLanguage = AppSettingsState.exerciseLanguage

    val logoutAndGoToLogin: () -> Unit = {
        scope.launch {
            AuthState.logout()
            RoutineState.reset()
            FavoritesState.reset()
            backStack.removeAll { true }
            backStack.add(Route.Login)
        }
    }

    LaunchedEffect(AuthState.currentUserId) {
        val userId = AuthState.currentUserId ?: return@LaunchedEffect
        RoutineState.markLoading(true)
        try {
            val routine = routineRepository.loadOrCreate(userId)
            RoutineState.loadFromRemote(routine.id, routine.name, routine.exercises)
            println("[Routine] Loaded '${routine.name}' with ${routine.exercises.size} exercises")
        } catch (e: Exception) {
            RoutineState.markLoading(false)
            println("[Routine] Failed to load: ${e.message}")
        }
        FavoritesState.markLoading(true)
        try {
            val favs = favoriteDataSource.getFavoritesByUser(userId)
            FavoritesState.loadFromRemote(favs.map { fav ->
                Exercise(
                    id = fav.exercise_id,
                    name = fav.exercise_name,
                    muscleGroup = MuscleGroup.fromString(fav.muscle_group),
                    instructions = fav.instructions
                )
            })
            println("[Favorites] Loaded ${favs.size} favorites")
        } catch (e: Exception) {
            FavoritesState.markLoading(false)
            println("[Favorites] Failed to load: ${e.message}")
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
            if (loadedExercises.isNotEmpty()) exercises = loadedExercises
        }.onFailure { error ->
            if (error is CancellationException) return@onFailure
            error.printStackTrace()
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.add(Route.Home)
                        backStack.removeAll { it is Route.Login }
                    }
                )
            }

            entry<Route.Home> {
                HomeScreen(
                    onStartClick = { backStack.add(Route.Exercises) },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) },
                    onNavigateToFavorites = { backStack.add(Route.Favorites) },
                    onNavigateToHistory = { backStack.add(Route.History) },
                    onNavigateToProfile = { backStack.add(Route.Profile) },
                    onNavigateToSettings = { backStack.add(Route.Settings) },
                    selectedLanguage = AppSettingsState.exerciseLanguage,
                    onLanguageChange = { language -> AppSettingsState.updateExerciseLanguage(language) },
                    exercises = exercises,
                    onApplyTemplate = { template ->
                        val resolved = PredefinedRoutines.resolve(template, exercises)
                        if (resolved.isNotEmpty()) {
                            RoutineState.applyTemplate(resolved)
                            backStack.add(Route.MyRoutine)
                        }
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
                    onAddToRoutine = { exercise -> RoutineState.addExercise(exercise) },
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
                WorkoutScreen(onBack = { backStack.removeLastOrNull() })
            }

            entry<Route.Favorites> {
                FavoritesScreen(
                    onExerciseClick = { id -> backStack.add(Route.ExerciseDetail(id)) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }

            entry<Route.History> {
                HistoryScreen(onBack = { backStack.removeLastOrNull() })
            }

            entry<Route.Profile> {
                ProfileScreen(
                    onBack = { backStack.removeLastOrNull() },
                    onLogout = logoutAndGoToLogin
                )
            }

            entry<Route.Settings> {
                SettingsScreen(onBack = { backStack.removeLastOrNull() })
            }
        }
    )
}
