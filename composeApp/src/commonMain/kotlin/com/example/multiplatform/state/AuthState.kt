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
    var currentEmail: String? by mutableStateOf(null)
        private set
    var isChecking by mutableStateOf(true)
        private set

    val isLoggedIn get() = currentUserId != null

    suspend fun init() {
        isChecking = true
        try {
            val auth = SupabaseClientProvider.client.auth
            delay(150)
            val user = auth.currentUserOrNull()
            currentUserId = user?.id
            currentEmail = user?.email
        } catch (e: Exception) {
            println("[Auth] Session restore failed: ${e.message}")
            currentUserId = null
            currentEmail = null
        }
        isChecking = false
    }

    suspend fun login(email: String, password: String) {
        SupabaseClientProvider.client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        val user = SupabaseClientProvider.client.auth.currentUserOrNull()
        currentUserId = user?.id
        currentEmail = user?.email
    }

    suspend fun signUp(email: String, password: String) {
        SupabaseClientProvider.client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        val user = SupabaseClientProvider.client.auth.currentUserOrNull()
        currentUserId = user?.id
        currentEmail = user?.email
    }

    suspend fun logout() {
        SupabaseClientProvider.client.auth.signOut()
        currentUserId = null
        currentEmail = null
    }
}
