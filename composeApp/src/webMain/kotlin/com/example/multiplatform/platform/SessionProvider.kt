package com.example.multiplatform.platform

actual object SessionProvider {
    actual fun getSessionId(): String = "web-${kotlin.random.Random.nextLong()}"
}
