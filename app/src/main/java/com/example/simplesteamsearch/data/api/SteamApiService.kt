package com.example.simplesteamsearch.data.api

import com.example.simplesteamsearch.data.model.SteamApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("ISteamApps/GetAppList/v2")
    suspend fun getAppList(): SteamApiResponse
}

