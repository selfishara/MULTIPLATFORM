package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.model.Routine

object RoutinesListState {

    private val _routines = mutableStateListOf<Routine>()
    var isLoading by mutableStateOf(false)
        private set

    val routines: List<Routine> get() = _routines

    fun loadFromRemote(routines: List<Routine>) {
        _routines.clear()
        _routines.addAll(routines)
        isLoading = false
    }

    fun markLoading(loading: Boolean) {
        isLoading = loading
    }

    fun addRoutine(routine: Routine) {
        _routines.add(routine)
    }

    fun removeRoutine(routineId: String) {
        _routines.removeAll { it.id == routineId }
    }

    fun reset() {
        _routines.clear()
        isLoading = false
    }
}
