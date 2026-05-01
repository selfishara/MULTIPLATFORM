package com.example.multiplatform

import androidx.compose.ui.window.ComposeUIViewController
import com.example.multiplatform.data.remote.NoOpExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseExerciseDataSource
import com.example.multiplatform.data.supabase.SupabaseExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseRoutineDataSource
import com.example.multiplatform.data.supabase.SupabaseRoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService

private val syncService = ExerciseSyncService(
    remoteRepository = NoOpExerciseRepository(),
    supabaseRepository = SupabaseExerciseRepository(SupabaseExerciseDataSource())
)
private val routineRepository = SupabaseRoutineRepository(SupabaseRoutineDataSource())

fun MainViewController() = ComposeUIViewController {
    App(
        exerciseSyncService = syncService,
        routineRepository = routineRepository
    )
}
