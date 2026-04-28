package com.example.multiplatform.data.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://ftclybraxvnghrydvofc.supabase.co"
    private const val SUPABASE_ANON_KEY = "sb_publishable_0P9QdnZs2gceo87zT5T15Q_jg5KX0cu"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
    }
}