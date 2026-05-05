package com.example.multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.multiplatform.data.WgerExerciseRepository
import com.example.multiplatform.data.remote.WgerApi
import com.example.multiplatform.data.remote.WgerDataSource
import com.example.multiplatform.data.supabase.SupabaseExerciseDataSource
import com.example.multiplatform.data.supabase.SupabaseExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseRoutineDataSource
import com.example.multiplatform.data.supabase.SupabaseRoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.network.HttpClientProvider

fun main() = application {
    val wgerRepository = WgerExerciseRepository(
        dataSource = WgerDataSource(
            api = WgerApi(HttpClientProvider.client)
        )
    )

    val syncService = ExerciseSyncService(
        remoteRepository = wgerRepository,
        supabaseRepository = SupabaseExerciseRepository(
            dataSource = SupabaseExerciseDataSource()
        )
    )

    val routineRepository = SupabaseRoutineRepository(
        dataSource = SupabaseRoutineDataSource()
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "GymSpot Lite"
    ) {
        App(
            exerciseSyncService = syncService,
            routineRepository = routineRepository
        )
    }
}
