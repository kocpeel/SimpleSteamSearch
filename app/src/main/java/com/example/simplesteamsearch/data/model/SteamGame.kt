package com.example.simplesteamsearch.data.model

data class SteamGame(
    val appid: Int,
    val name: String,
    val headerImage: String,
    val price: String?,
    val discount: Int?,
    val finalPrice: String?
) 