package com.example.thefinaltransfer.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

private const val SUPABASE_URL = "https://wdimfhfbzpmvegnlvmxo.supabase.co"
private const val SUPABASE_ANON_KEY = "sb_publishable_Ebcym6v7x5lToLxoXP6jNg_kQfl0ehM"

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Storage)
    }
}