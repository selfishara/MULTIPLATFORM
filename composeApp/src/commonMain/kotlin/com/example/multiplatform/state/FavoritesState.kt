package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.data.supabase.FavoriteDataSource
import com.example.multiplatform.model.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object FavoritesState {

    private val _favorites = mutableStateListOf<Exercise>()
    val favorites: List<Exercise> get() = _favorites

    var isLoading by mutableStateOf(false)
        private set

    private var dataSource: FavoriteDataSource? = null
    private var scope: CoroutineScope? = null

    fun init(ds: FavoriteDataSource, coroutineScope: CoroutineScope) {
        dataSource = ds
        scope = coroutineScope
    }

    fun markLoading(loading: Boolean) {
        isLoading = loading
    }

    fun loadFromRemote(exercises: List<Exercise>) {
        _favorites.clear()
        _favorites.addAll(exercises)
        isLoading = false
    }

    fun isFavorite(exerciseId: String) = _favorites.any { it.id == exerciseId }

    fun toggle(exercise: Exercise) {
        val userId = AuthState.currentUserId ?: return
        if (isFavorite(exercise.id)) {
            _favorites.removeAll { it.id == exercise.id }
            sync { dataSource?.removeFavorite(userId, exercise.id) }
        } else {
            _favorites.add(exercise)
            sync { dataSource?.addFavorite(userId, exercise) }
        }
    }

    fun reset() {
        _favorites.clear()
        isLoading = false
    }

    private fun sync(block: suspend () -> Unit) {
        scope?.launch {
            try {
                block()
            } catch (e: Exception) {
                println("[Favorites] Sync error: ${e.message}")
            }
        }
    }
}
