package com.example.simplesteamsearch.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.simplesteamsearch.data.model.SteamGame

@Composable
fun GameItem(game: SteamGame) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = game.headerImage,
                contentDescription = game.name,
                modifier = Modifier
                    .size(80.dp),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleMedium
                )
                
                if (game.finalPrice != null) {
                    Text(
                        text = game.finalPrice,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                if (game.discount != null && game.discount > 0) {
                    Text(
                        text = "-${game.discount}%",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
} 