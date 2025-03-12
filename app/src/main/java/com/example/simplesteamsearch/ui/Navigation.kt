package com.example.simplesteamsearch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simplesteamsearch.data.model.Game
import com.example.simplesteamsearch.ui.screens.GameDetailsScreen
import com.example.simplesteamsearch.ui.screens.PopularGamesScreen
import com.example.simplesteamsearch.ui.screens.WishlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val wishlist = remember { mutableStateListOf<Game>() }

    NavHost(navController = navController, startDestination = "popularGames") {
        composable("popularGames") {
            PopularGamesScreen(
                onGameClick = { appid -> navController.navigate("gameDetails/$appid") },
                onWishlistClick = { game ->
                    if (wishlist.contains(game)) {
                        wishlist.remove(game)
                    } else {
                        wishlist.add(game)
                    }
                },
                onWishlistScreenClick = { navController.navigate("wishlist") }
            )
        }
        composable(
            "gameDetails/{appid}",
            arguments = listOf(navArgument("appid") { type = NavType.StringType })
        ) { backStackEntry ->
            val appid = backStackEntry.arguments?.getString("appid") ?: ""
            GameDetailsScreen(
                appid = appid,
                onBackClick = { navController.popBackStack() },
                onWishlistClick = { game ->
                    if (wishlist.contains(game)) {
                        wishlist.remove(game)
                    } else {
                        wishlist.add(game)
                    }
                }
            )
        }
        composable("wishlist") {
            WishlistScreen(
                onGameClick = { appid -> navController.navigate("gameDetails/$appid") },
                onBackClick = { navController.popBackStack() },
                onWishlistClick = { game ->
                    if (wishlist.contains(game)) {
                        wishlist.remove(game)
                    } else {
                        wishlist.add(game)
                    }
                }
            )
        }
    }
}