package com.example.multiplatform.platform

actual object AuthTokenStore {
    private var stored: String? = null

    actual fun save(json: String) { stored = json }
    actual fun load(): String? = stored
    actual fun clear() { stored = null }
}
