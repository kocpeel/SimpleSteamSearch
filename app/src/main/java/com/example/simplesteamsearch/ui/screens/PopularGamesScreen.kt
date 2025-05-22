package com.example.simplesteamsearch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplesteamsearch.data.model.Game
import com.example.simplesteamsearch.ui.components.GameCard
import com.example.simplesteamsearch.viewmodel.SteamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularGamesScreen(
    onGameClick: (String) -> Unit,
    onWishlistClick: (Game) -> Unit,
    onWishlistScreenClick: () -> Unit
) {
    val steamViewModel: SteamViewModel = viewModel()
    val popularGames by steamViewModel.popularGames.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        steamViewModel.fetchPopularGames()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Popular Games") },
                actions = {
                    IconButton(onClick = onWishlistScreenClick) {
                        Icon(Icons.Filled.List, contentDescription = "Wishlist")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(popularGames) { game ->
                    GameCard(
                        game = game,
                        onGameClick = onGameClick,
                        onWishlistClick = onWishlistClick
                    )
                }
            }
        }
    }
}