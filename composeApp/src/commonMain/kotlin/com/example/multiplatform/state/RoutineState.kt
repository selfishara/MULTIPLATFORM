package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.data.routine.RoutineRepository
import com.example.multiplatform.model.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object RoutineState {

    private val _routine = mutableStateListOf<Exercise>()
    var name by mutableStateOf("My Routine")
        private set
    var isLoading by mutableStateOf(false)
        private set

    val routine: List<Exercise> get() = _routine

    private var routineId: String? = null
    private var repository: RoutineRepository? = null
    private var scope: CoroutineScope? = null

    fun init(repo: RoutineRepository, coroutineScope: CoroutineScope) {
        repository = repo
        scope = coroutineScope
    }

    fun loadFromRemote(id: String, routineName: String, exercises: List<Exercise>) {
        routineId = id
        name = routineName
        _routine.clear()
        _routine.addAll(exercises)
        isLoading = false
    }

    fun markLoading(loading: Boolean) {
        isLoading = loading
    }

    fun addExercise(exercise: Exercise) {
        if (_routine.none { it.id == exercise.id }) {
            val position = _routine.size
            _routine.add(exercise)
            sync {
                repository?.addExercise(routineId!!, exercise, position)
            }
        }
    }

    fun removeExercise(exercise: Exercise) {
        _routine.removeAll { it.id == exercise.id }
        sync {
            repository?.removeExercise(routineId!!, exercise.id)
        }
    }

    fun clearRoutine() {
        _routine.clear()
        sync {
            repository?.clearExercises(routineId!!)
        }
    }

    fun renameRoutine(newName: String) {
        name = newName
        sync {
            repository?.rename(routineId!!, newName)
        }
    }

    private fun sync(block: suspend () -> Unit) {
        val id = routineId ?: return
        scope?.launch {
            try {
                block()
            } catch (e: Exception) {
                println("[Routine] Sync error for routine $id: ${e.message}")
            }
        }
    }
}
