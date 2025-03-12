package com.example.simplesteamsearch.data.repository

import android.util.Log
import com.example.simplesteamsearch.data.model.Game
import com.example.simplesteamsearch.data.model.GameDetails
import com.example.simplesteamsearch.data.network.SteamApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class SteamRepository {
    private val steamApiService = SteamApi.steamApiService

    suspend fun getPopularGames(): List<Game>? = withContext(Dispatchers.IO) {
        try {
            val response = steamApiService.getPopularGames()
            if (response.isSuccessful) {
                val data = response.body()
                val games = mutableListOf<Game>()
                val items = data?.get("items") as? List<Map<String, Any>> ?: return@withContext null

                for (item in items) {
                    try {
                        val logoUrl = item["logo"] as? String ?: continue
                        val appidMatch = Pattern.compile("steam/\\w+/(\\d+)").matcher(logoUrl)
                        val appid = if (appidMatch.find()) appidMatch.group(1) else continue

                        val gameDetailsResponse = steamApiService.getGameDetails(appid)
                        val gameDetailsData = gameDetailsResponse.body()?.get(appid)?.get("data") as? Map<String, Any>
                        val priceData = gameDetailsData?.get("price_overview") as? Map<String, Any>
                        val price = if (gameDetailsData?.get("is_free") == true) {
                            "Free"
                        } else {
                            priceData?.get("final")?.let { (it as Double / 100).toString() + "zł" } ?: "Price not available"
                        }

                        val publisher = gameDetailsData?.get("publishers")?.let {
                            if (it is List<*>) {
                                it.firstOrNull() as? String ?: "WYDAWCA"
                            } else {
                                "WYDAWCA"
                            }
                        } ?: "WYDAWCA"

                        val currentPlayersResponse = steamApiService.getCurrentPlayers(appid)
                        val currentPlayers = currentPlayersResponse.body()?.get("response")?.get("player_count") ?: 0

                        val description = gameDetailsData?.get("short_description")?.let {
                            val words = (it as String).split(" ")
                            if (words.size > 30) {
                                words.take(30).joinToString(" ") + "..."
                            } else {
                                it
                            }
                        } ?: "DESCRIPTION"

                        val game = Game(
                            name = item["name"] as? String ?: "TYTUŁ",
                            appid = appid,
                            price = price,
                            publisher = publisher,
                            players = currentPlayers,
                            description = description
                        )
                        games.add(game)
                    } catch (e: Exception) {
                        Log.e("SteamRepository", "Error processing game item: ${e.message}")
                    }
                }
                games.take(10)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("SteamRepository", "Error fetching popular games: ${e.message}")
            null
        }
    }

    suspend fun getGameDetails(appid: String): GameDetails? = withContext(Dispatchers.IO) {
        try {
            val response = steamApiService.getGameDetails(appid)
            if (response.isSuccessful) {
                val gameDetailsData = response.body()?.get(appid)?.get("data") as? Map<String, Any>
                val description = gameDetailsData?.get("about_the_game") as? String ?: "DESCRIPTION"
                val currentPlayersResponse = steamApiService.getCurrentPlayers(appid)
                val currentPlayers = currentPlayersResponse.body()?.get("response")?.get("player_count") ?: 0
                val requirements = gameDetailsData?.get("pc_requirements")?.get("minimum") as? String ?: "WYMAGANIA SPRZETOWE"

                GameDetails(description, currentPlayers, requirements)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("SteamRepository", "Error fetching game details: ${e.message}")
            null
        }
    }
}