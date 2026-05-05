package com.example.multiplatform.platform

import java.io.File

actual object AuthTokenStore {
    private val file = File(System.getProperty("user.home"), ".gymspot_auth_session")

    actual fun save(json: String) {
        file.writeText(json)
    }

    actual fun load(): String? = if (file.exists()) file.readText() else null

    actual fun clear() {
        file.delete()
    }
}
