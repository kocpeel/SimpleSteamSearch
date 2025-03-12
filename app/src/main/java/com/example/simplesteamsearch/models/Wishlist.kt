package com.example.simplesteamsearch.models

data class Wishlist(
    val games: MutableList<SteamGame> = mutableListOf()
) {
    fun addGame(game: SteamGame) {
        games.add(game)
    }

    fun removeGame(gameId: Int) {
        games.removeIf { it.id == gameId }
    }

    fun contains(gameId: Int): Boolean {
        return games.any { it.id == gameId }
    }

    operator fun get(index: Int): SteamGame {
        return games[index]
    }
}