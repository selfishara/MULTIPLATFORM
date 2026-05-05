package com.example.multiplatform.platform

import android.content.Context
import java.util.UUID

actual object SessionProvider {
    private var sessionId: String? = null

    fun initialize(context: Context) {
        val prefs = context.getSharedPreferences("gymspot", Context.MODE_PRIVATE)
        sessionId = prefs.getString("session_id", null)
            ?: UUID.randomUUID().toString().also { id ->
                prefs.edit().putString("session_id", id).apply()
            }
    }

    actual fun getSessionId(): String = sessionId ?: UUID.randomUUID().toString()
}
