package com.example.simplesteamsearch.data.model

data class Game(
    val name: String = "TYTUŁ",
    val appid: String = "",
    val price: String = "CENA",
    val publisher: String = "WYDAWCA",
    val players: Int = 0,
    val description: String = "DESCRIPTION"
)