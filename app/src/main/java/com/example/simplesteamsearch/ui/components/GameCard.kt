package com.example.simplesteamsearch.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simplesteamsearch.data.model.Game

@Composable
fun GameCard(game: Game, onGameClick: (String) -> Unit, onWishlistClick: (Game) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onGameClick(game.appid) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = game.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Price: ${game.price}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Publisher: ${game.publisher}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Players: ${game.players}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Description: ${game.description}", style = MaterialTheme.typography.bodyMedium)
            Row {
                Spacer(modifier = Modifier.weight(1f))
                WishlistButton(game = game, onWishlistClick = onWishlistClick)
            }
        }
    }
}