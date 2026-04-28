package com.example.multiplatform.data.sync

import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseExerciseRepository
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage

class ExerciseSyncService(
    private val remoteRepository: ExerciseRepository,
    private val supabaseRepository: SupabaseExerciseRepository
) : ExerciseRepository {

    override suspend fun getExercises(
        preferredLanguage: ExerciseLanguage
    ): List<Exercise> {

        val cachedExercises = supabaseRepository.getExercises(preferredLanguage)

        if (cachedExercises.isNotEmpty()) {
            println("Using Supabase cache")
            return cachedExercises
        }

        println("Supabase empty. Fetching from Wger...")

        val remoteExercises = remoteRepository.getExercises(preferredLanguage)

        if (remoteExercises.isNotEmpty()) {
            supabaseRepository.cacheExercises(
                exercises = remoteExercises,
                language = preferredLanguage
            )

            println("Saved ${remoteExercises.size} exercises to Supabase")
        }

        return remoteExercises
    }
}