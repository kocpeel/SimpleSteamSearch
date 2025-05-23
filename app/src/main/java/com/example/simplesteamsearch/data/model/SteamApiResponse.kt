package com.example.simplesteamsearch.data.model

data class SteamApiResponse(
    val applist: AppList
)

data class AppList(
    val apps: List<App>
)

data class App(
    val appid: Int,
    val name: String
) 