package com.example.multiplatform.navigation

/**
 * Navigation wrapper that manages the application's navigation flow.
 *
 * Handles the back stack and routes between all screens including Home, Exercises list,
 * Exercise detail view, My Routine screen, and Workout mode. Uses Navigation 3 for
 * managing the navigation state and transitions. Integrates with RoutineState for
 * exercise management.
 */
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

import com.example.multiplatform.screens.ExerciseDetailScreen
import com.example.multiplatform.screens.ExercisesScreen
import com.example.multiplatform.screens.HomeScreen
import com.example.multiplatform.screens.MyRoutineScreen
import com.example.multiplatform.screens.WorkoutScreen
import com.example.multiplatform.state.RoutineState

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
                     },
                     onNavigateToRoutine = {
                         backStack.add(Route.MyRoutine)
                     }
                 )
             }

             entry<Route.Exercises> {
                 ExercisesScreen(
                     onExerciseClick = { exerciseId ->
                         backStack.add(Route.ExerciseDetail(exerciseId))
                     },
                     onAddToRoutine = { exercise ->
                         RoutineState.addExercise(exercise)
                     },
                     onBack = {
                         backStack.removeLastOrNull()
                     }
                 )
             }

             entry<Route.ExerciseDetail> { key ->
                 ExerciseDetailScreen(
                     exerciseId = key.exerciseId,
                     onAddToRoutine = { exercise ->
                         RoutineState.addExercise(exercise)
                     },
                     onViewRoutine = {
                         backStack.add(Route.MyRoutine)
                     },
                     onBack = {
                         backStack.removeLastOrNull()
                     }
                 )
             }

             entry<Route.MyRoutine> {
                 MyRoutineScreen(
                     onBack = {
                         backStack.removeLastOrNull()
                     },
                     onStartWorkout = {
                         backStack.add(Route.Workout)
                     }
                 )
             }

             entry<Route.Workout> {
                 WorkoutScreen(
                     onBack = {
                         backStack.removeLastOrNull()
                     }
                 )
             }
         }
    )
}