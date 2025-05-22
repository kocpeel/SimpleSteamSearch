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
    private val TAG = "SteamRepository"

    suspend fun getPopularGames(): List<Game>? = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Fetching popular games...")
            val response = steamApiService.getPopularGames()
            if (!response.isSuccessful) {
                Log.e(TAG, "Response not successful: ${response.code()}")
                return@withContext null
            }

            Log.d(TAG, "Response successful")
            val data = response.body() ?: run {
                Log.e(TAG, "Response body is null")
                return@withContext null
            }

            val games = mutableListOf<Game>()
            @Suppress("UNCHECKED_CAST")
            val items = data["items"] as? List<Map<String, Any>> ?: run {
                Log.e(TAG, "Items list is null or invalid")
                return@withContext null
            }
            Log.d(TAG, "Found ${items.size} items")

            for (item in items) {
                try {
                    val logoUrl = item["logo"] as? String
                    if (logoUrl == null) {
                        Log.e(TAG, "Logo URL is null")
                        continue
                    }

                    val appidMatch = Pattern.compile("steam/\\w+/(\\d+)").matcher(logoUrl)
                    val appid = if (appidMatch.find()) appidMatch.group(1) else {
                        Log.e(TAG, "Could not extract appid from URL: $logoUrl")
                        continue
                    }
                    Log.d(TAG, "Processing game with appid: $appid")

                    val gameDetailsResponse = steamApiService.getGameDetails(appid)
                    if (!gameDetailsResponse.isSuccessful) {
                        Log.e(TAG, "Game details response not successful: ${gameDetailsResponse.code()}")
                        continue
                    }

                    @Suppress("UNCHECKED_CAST")
                    val gameDetailsMap = gameDetailsResponse.body()
                    if (gameDetailsMap == null) {
                        Log.e(TAG, "Game details body is null")
                        continue
                    }

                    @Suppress("UNCHECKED_CAST")
                    val gameDetailsData = (gameDetailsMap[appid] as? Map<String, Any>)?.get("data") as? Map<String, Any>
                    if (gameDetailsData == null) {
                        Log.e(TAG, "Game details data is null or invalid")
                        continue
                    }

                    @Suppress("UNCHECKED_CAST")
                    val priceData = gameDetailsData["price_overview"] as? Map<String, Any>
                    val price = if (gameDetailsData["is_free"] == true) {
                        "Free"
                    } else {
                        priceData?.get("final")?.let { (it as Double / 100).toString() + "zł" } ?: "Price not available"
                    }

                    val publisher = gameDetailsData["publishers"]?.let {
                        if (it is List<*>) {
                            it.firstOrNull() as? String ?: "WYDAWCA"
                        } else {
                            "WYDAWCA"
                        }
                    } ?: "WYDAWCA"

                    val currentPlayersResponse = steamApiService.getCurrentPlayers(appid)
                    if (!currentPlayersResponse.isSuccessful) {
                        Log.e(TAG, "Current players response not successful: ${currentPlayersResponse.code()}")
                        continue
                    }

                    @Suppress("UNCHECKED_CAST")
                    val playersMap = currentPlayersResponse.body()
                    if (playersMap == null) {
                        Log.e(TAG, "Players map is null")
                        continue
                    }

                    @Suppress("UNCHECKED_CAST")
                    val responseMap = playersMap["response"] as? Map<String, Any>
                    if (responseMap == null) {
                        Log.e(TAG, "Response map is null or invalid")
                        continue
                    }

                    val currentPlayers = responseMap["player_count"] as? Int ?: 0

                    val description = gameDetailsData["short_description"]?.let {
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
                    Log.d(TAG, "Successfully added game: ${game.name}")
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing game item: ${e.message}", e)
                }
            }
            Log.d(TAG, "Returning ${games.size} games")
            games.take(10)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching popular games: ${e.message}", e)
            null
        }
    }

    suspend fun getGameDetails(appid: String): GameDetails? = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Fetching game details for appid: $appid")
            val response = steamApiService.getGameDetails(appid)
            if (!response.isSuccessful) {
                Log.e(TAG, "Response not successful: ${response.code()}")
                return@withContext null
            }

            @Suppress("UNCHECKED_CAST")
            val gameDetailsMap = response.body()
            if (gameDetailsMap == null) {
                Log.e(TAG, "Game details body is null")
                return@withContext null
            }

            @Suppress("UNCHECKED_CAST")
            val gameDetailsData = (gameDetailsMap[appid] as? Map<String, Any>)?.get("data") as? Map<String, Any>
            if (gameDetailsData == null) {
                Log.e(TAG, "Game details data is null or invalid")
                return@withContext null
            }

            val description = gameDetailsData["about_the_game"] as? String ?: "DESCRIPTION"
            
            val currentPlayersResponse = steamApiService.getCurrentPlayers(appid)
            if (!currentPlayersResponse.isSuccessful) {
                Log.e(TAG, "Current players response not successful: ${currentPlayersResponse.code()}")
                return@withContext null
            }

            @Suppress("UNCHECKED_CAST")
            val playersMap = currentPlayersResponse.body()
            if (playersMap == null) {
                Log.e(TAG, "Players map is null")
                return@withContext null
            }

            @Suppress("UNCHECKED_CAST")
            val responseMap = playersMap["response"] as? Map<String, Any>
            if (responseMap == null) {
                Log.e(TAG, "Response map is null or invalid")
                return@withContext null
            }

            val currentPlayers = responseMap["player_count"] as? Int ?: 0
            
            @Suppress("UNCHECKED_CAST")
            val requirements = (gameDetailsData["pc_requirements"] as? Map<String, Any>)?.get("minimum") as? String ?: "WYMAGANIA SPRZETOWE"

            Log.d(TAG, "Successfully fetched game details for appid: $appid")
            GameDetails(description, currentPlayers, requirements)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching game details: ${e.message}", e)
            null
        }
    }
}