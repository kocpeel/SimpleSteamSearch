package com.example.simplesteamsearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplesteamsearch.ui.screens.GameDetailsScreen
import com.example.simplesteamsearch.ui.screens.PopularGamesScreen
import com.example.simplesteamsearch.ui.screens.WishlistScreen
import com.example.simplesteamsearch.ui.theme.SimpleSteamSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleSteamSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "popular_games") {
        composable("popular_games") {
            PopularGamesScreen(
                onGameClick = { appid ->
                    navController.navigate("game_details/$appid")
                },
                onWishlistClick = { _ ->
                    // Handle wishlist click
                },
                onWishlistScreenClick = {
                    navController.navigate("wishlist")
                }
            )
        }
        composable("game_details/{appid}") { backStackEntry ->
            val appid = backStackEntry.arguments?.getString("appid") ?: return@composable
            GameDetailsScreen(
                appid = appid,
                onBackClick = {
                    navController.popBackStack()
                },
                onWishlistClick = { _ ->
                    // Handle wishlist click
                }
            )
        }
        composable("wishlist") {
            WishlistScreen(
                onGameClick = { appid ->
                    navController.navigate("game_details/$appid")
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onWishlistClick = { _ ->
                    // Handle wishlist click
                }
            )
        }
    }
}