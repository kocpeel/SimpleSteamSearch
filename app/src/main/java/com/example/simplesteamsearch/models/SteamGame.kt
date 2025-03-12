package com.example.simplesteamsearch.models

data class SteamGame(
    val id: Int,
    val name: String,
    val logoUrl: String,
    val price: Double,
    val publisher: String,
    val currentPlayers: Int,
    val description: String?
)