package com.example.simplesteamsearch.data.network

import com.example.simplesteamsearch.data.model.GameDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("search/results/")
    suspend fun getPopularGames(
        @Query("filter") filter: String = "topsellers",
        @Query("hidef2p") hidef2p: Int = 1,
        @Query("page") page: Int = 1,
        @Query("json") json: Int = 1
    ): Response<Map<String, Any>>

    @GET("api/appdetails")
    suspend fun getGameDetails(
        @Query("appids") appids: String,
        @Query("cc") cc: String = "pl",
        @Query("l") l: String = "polish"
    ): Response<Map<String, Any>>

    @GET("ISteamUserStats/GetNumberOfCurrentPlayers/v1/")
    suspend fun getCurrentPlayers(
        @Query("appid") appid: String
    ): Response<Map<String, Any>>
}