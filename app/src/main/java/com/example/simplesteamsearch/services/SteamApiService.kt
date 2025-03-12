package com.example.simplesteamsearch.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.ConcurrentHashMap

object SteamApiService {
    private const val BASE_URL = "https://api.steampowered.com/"

    private val apiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val cache = ConcurrentHashMap<String, Any>()

    suspend fun <T> callApi(endpoint: String, body: T?): T {
        val key = "$endpoint:${body.toString()}"
        return withContext(Dispatchers.IO) {
            if (cache.containsKey(key)) {
                cache[key] as T
            } else {
                val response = apiClient.create<SteamApi>().execute(endpoint, body)
                cache[key] = response
                response
            }
        }
    }

    interface SteamApi {
        suspend fun fetchPopularGames(): List<SteamGame>
        suspend fun fetchGameDetails(id: Int): SteamGame
        suspend fun fetchWishlist(): List<SteamGame>
    }

    data class SteamGame(
        val id: Int,
        val name: String,
        val logoUrl: String,
        val price: Double,
        val publisher: String,
        val currentPlayers: Int,
        val description: String?
    )
}