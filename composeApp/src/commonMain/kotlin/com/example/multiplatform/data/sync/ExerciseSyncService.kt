package com.example.multiplatform.data.sync

import com.example.multiplatform.data.remote.ExerciseRepository
import com.example.multiplatform.data.supabase.SupabaseExerciseRepository
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseSyncService(
    private val remoteRepository: ExerciseRepository,
    private val supabaseRepository: SupabaseExerciseRepository
) : ExerciseRepository {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    override suspend fun getExercises(preferredLanguage: ExerciseLanguage): List<Exercise> {
        _syncState.value = SyncState.Loading

        val cacheExists = try {
            supabaseRepository.existsByLanguage(preferredLanguage)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            println("[Sync] Cache check failed for ${preferredLanguage.apiCode}: ${e.message}")
            false
        }

        if (cacheExists) {
            return try {
                val cached = supabaseRepository.getExercises(preferredLanguage)
                if (cached.isNotEmpty()) {
                    _syncState.value = SyncState.Success(
                        source = SyncSource.CACHE,
                        count = cached.size,
                        language = preferredLanguage.apiCode
                    )
                    println("[Sync] ${cached.size} exercises from cache (${preferredLanguage.apiCode})")
                    return cached
                }
                fetchFromApiAndCache(preferredLanguage)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                println("[Sync] Cache read error for ${preferredLanguage.apiCode}: ${e.message}")
                fetchFromApiAndCache(preferredLanguage)
            }
        }

        return fetchFromApiAndCache(preferredLanguage)
    }

    private suspend fun fetchFromApiAndCache(language: ExerciseLanguage): List<Exercise> {
        return try {
            println("[Sync] Cache miss — fetching ${language.apiCode} from Wger API...")
            val remote = remoteRepository.getExercises(language)

            if (remote.isNotEmpty()) {
                try {
                    supabaseRepository.cacheExercises(remote, language)
                    println("[Sync] Cached ${remote.size} exercises to Supabase (${language.apiCode})")
                } catch (e: Exception) {
                    println("[Sync] WARNING: Supabase write failed: ${e.message}")
                }
                _syncState.value = SyncState.Success(
                    source = SyncSource.API,
                    count = remote.size,
                    language = language.apiCode
                )
            } else {
                _syncState.value = SyncState.Error("No exercises returned for ${language.apiCode}")
            }

            remote
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            println("[Sync] Wger API failed for ${language.apiCode}: ${e.message}")

            // Last resort: try reading cache even if the exists-check said false
            val fallback = try {
                supabaseRepository.getExercises(language)
            } catch (_: Exception) {
                emptyList()
            }

            _syncState.value = if (fallback.isNotEmpty()) {
                SyncState.Error(
                    message = "API unavailable — showing cached exercises",
                    hasFallback = true,
                    fallbackCount = fallback.size
                )
            } else {
                SyncState.Error("Could not load exercises: ${e.message}")
            }

            fallback
        }
    }
}
