package com.example.multiplatform.data.supabase

import com.example.multiplatform.platform.AuthTokenStore
import io.github.jan.supabase.auth.SessionManager
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PersistentSessionManager : SessionManager {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    override suspend fun saveSession(session: UserSession) {
        runCatching { AuthTokenStore.save(json.encodeToString(session)) }
    }

    override suspend fun loadSession(): UserSession? {
        val stored = AuthTokenStore.load() ?: return null
        return runCatching { json.decodeFromString<UserSession>(stored) }.getOrNull()
    }

    override suspend fun deleteSession() {
        AuthTokenStore.clear()
    }
}
