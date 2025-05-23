package com.example.simplesteamsearch.data.api

import com.example.simplesteamsearch.data.model.SteamGame
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("ISteamApps/GetAppList/v2")
    suspend fun searchGames(
        @Query("search") query: String
    ): Map<String, List<SteamGame>>
} 