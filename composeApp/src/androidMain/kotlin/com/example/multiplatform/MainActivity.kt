package com.example.multiplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.multiplatform.data.remote.NoOpExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseExerciseDataSource
import com.example.multiplatform.data.supabase.SupabaseExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseRoutineDataSource
import com.example.multiplatform.data.supabase.SupabaseRoutineRepository
import com.example.multiplatform.data.sync.ExerciseSyncService
import com.example.multiplatform.platform.AuthTokenStore
import com.example.multiplatform.platform.SessionProvider

class MainActivity : ComponentActivity() {

    private lateinit var syncService: ExerciseSyncService
    private lateinit var routineRepository: SupabaseRoutineRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        SessionProvider.initialize(this)
        AuthTokenStore.initialize(this)

        syncService = ExerciseSyncService(
            remoteRepository = NoOpExerciseRepository(),
            supabaseRepository = SupabaseExerciseRepository(SupabaseExerciseDataSource())
        )
        routineRepository = SupabaseRoutineRepository(SupabaseRoutineDataSource())

        setContent {
            App(
                exerciseSyncService = syncService,
                routineRepository = routineRepository
            )
        }
    }
}
