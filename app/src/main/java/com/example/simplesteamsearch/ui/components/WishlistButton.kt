package com.example.simplesteamsearch.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.simplesteamsearch.data.model.Game

@Composable
fun WishlistButton(game: Game, onWishlistClick: (Game) -> Unit) {
    var isWishlisted by remember { mutableStateOf(false) }

    IconButton(onClick = {
        isWishlisted = !isWishlisted
        onWishlistClick(game)
    }) {
        Icon(
            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Add to Wishlist",
        )
    }
}