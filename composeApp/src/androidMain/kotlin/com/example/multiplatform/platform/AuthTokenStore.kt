package com.example.multiplatform.platform

import android.content.Context

actual object AuthTokenStore {
    private var context: Context? = null

    fun initialize(ctx: Context) {
        context = ctx.applicationContext
    }

    actual fun save(json: String) {
        context?.getSharedPreferences("gymspot_auth", Context.MODE_PRIVATE)
            ?.edit()?.putString("session", json)?.apply()
    }

    actual fun load(): String? =
        context?.getSharedPreferences("gymspot_auth", Context.MODE_PRIVATE)
            ?.getString("session", null)

    actual fun clear() {
        context?.getSharedPreferences("gymspot_auth", Context.MODE_PRIVATE)
            ?.edit()?.remove("session")?.apply()
    }
}
