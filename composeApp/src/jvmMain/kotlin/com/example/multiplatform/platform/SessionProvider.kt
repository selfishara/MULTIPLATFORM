package com.example.multiplatform.platform

import java.io.File
import java.util.UUID

actual object SessionProvider {
    private val sessionFile = File(System.getProperty("user.home"), ".gymspot_session")

    actual fun getSessionId(): String {
        return if (sessionFile.exists()) {
            sessionFile.readText().trim()
        } else {
            val id = UUID.randomUUID().toString()
            sessionFile.writeText(id)
            id
        }
    }
}
