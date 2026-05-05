package com.example.multiplatform.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.multiplatform.data.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.delay

object AuthState {

    var currentUserId: String? by mutableStateOf(null)
        private set
    var isChecking by mutableStateOf(true)
        private set

    val isLoggedIn get() = currentUserId != null

    suspend fun init() {
        isChecking = true
        currentUserId = try {
            val auth = SupabaseClientProvider.client.auth
            // Allow the Auth plugin's session-loading coroutine to run first
            delay(150)
            auth.currentUserOrNull()?.id
        } catch (e: Exception) {
            println("[Auth] Session restore failed: ${e.message}")
            null
        }
        isChecking = false
    }

    suspend fun login(email: String, password: String) {
        SupabaseClientProvider.client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        currentUserId = SupabaseClientProvider.client.auth.currentUserOrNull()?.id
    }

    suspend fun signUp(email: String, password: String) {
        SupabaseClientProvider.client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        currentUserId = SupabaseClientProvider.client.auth.currentUserOrNull()?.id
    }

    suspend fun logout() {
        SupabaseClientProvider.client.auth.signOut()
        currentUserId = null
    }
}
