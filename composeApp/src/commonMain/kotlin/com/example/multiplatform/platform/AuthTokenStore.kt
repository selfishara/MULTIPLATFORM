package com.example.multiplatform.platform

expect object AuthTokenStore {
    fun save(json: String)
    fun load(): String?
    fun clear()
}
