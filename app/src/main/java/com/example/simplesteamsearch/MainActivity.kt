package com.example.simplesteamsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplesteamsearch.screens.PopularGamesScreen
import com.example.simplesteamsearch.screens.GameDetailsScreen
import com.example.simplesteamsearch.screens.WishlistScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleSteamSearchTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    BottomNavigationMenu(
                        onNavigate = { destination ->
                            when (destination) {
                                PopularGamesScreen -> navigateToPopularGames()
                                GameDetailsScreen -> navigateToGameDetails()
                                WishlistScreen -> navigateToWishlist()
                            }
                        }
                    )
                    PopularGamesScreen()
                }
            }
        }
    }

    private fun navigateToPopularGames() {
        val navController = findNavController()
        navController.navigate("popularGames")
    }

    private fun navigateToGameDetails(gameId: Int) {
        val navController = findNavController()
        navController.navigate("gameDetails/$gameId")
    }

    private fun navigateToWishlist() {
        val navController = findNavController()
        navController.navigate("wishlist")
    }
}