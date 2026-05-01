package com.example.multiplatform.platform

actual object SessionProvider {
    actual fun getSessionId(): String = "ios-${kotlin.random.Random.nextLong()}"
}
