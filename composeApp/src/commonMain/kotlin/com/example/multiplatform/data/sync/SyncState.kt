package com.example.multiplatform.data.sync

sealed class SyncState {
    object Idle : SyncState()
    object Loading : SyncState()
    data class Success(
        val source: SyncSource,
        val count: Int,
        val language: String
    ) : SyncState()
    data class Error(
        val message: String,
        val hasFallback: Boolean = false,
        val fallbackCount: Int = 0
    ) : SyncState()
}

enum class SyncSource(val label: String) {
    CACHE("cache"),
    API("Wger API"),
    CACHE_FALLBACK("cache (API unavailable)")
}
