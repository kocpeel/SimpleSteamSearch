package com.example.simplesteamsearch.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.simplesteamsearch.models.SteamGame
import com.example.simplesteamsearch.services.SteamApiService

@Composable
fun GameDetailsScreen(gameId: Int) {
    val gameDetails = remember { mutableStateMapOf<Int, SteamGame>() }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        fetchGameDetails(gameId, gameDetails, isLoading, error)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        } else if (error.value != null) {
            Text(text = error.value ?: "", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxHeight().padding(vertical = 16.dp)) {
                items(gameDetails.entries) { entry ->
                    GameDetailsListItem(entry.value) {
                        navigateToWishlist(entry.key)
                    }
                }
            }
        }
    }
}

private suspend fun fetchGameDetails(
    gameId: Int,
    gameDetails: MutableMap<Int, SteamGame>,
    isLoading: MutableState<Boolean>,
    error: MutableState<String?>
) {
    isLoading.value = true
    try {
        val apiResponse = SteamApiService.fetchGameDetails(gameId)
        gameDetails.clear()
        gameDetails[gameId] = apiSystemRequirements(apiResponse)
    } catch (e: Exception) {
        Log.e("GameDetailsScreen", "Error fetching game details", e)
        error.value = "An error occurred while loading game details."
    } finally {
        isLoading.value = false
    }
}

@Composable
fun GameDetailsListItem(game: SteamGame, onItemClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberPainter(game.logoUrl),
                contentDescription = "Game Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${game.price} zł",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Publisher: ${game.publisher}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Players: ${game.currentPlayers}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = game.description ?: "",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}