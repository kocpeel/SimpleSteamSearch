package com.example.simplesteamsearch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import com.example.simplesteamsearch.ui.components.WishlistButton
import com.example.simplesteamsearch.viewmodel.SteamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    appid: String,
    onBackClick: () -> Unit,
    onWishlistClick: (Game) -> Unit
) {
    val steamViewModel: SteamViewModel = viewModel()
    val gameDetails by steamViewModel.gameDetails.observeAsState()

    LaunchedEffect(key1 = appid) {
        steamViewModel.fetchGameDetails(appid)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Game Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp)
        ) {
            gameDetails?.let { details ->
                Text(text = "Description: ${details.description}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Players: ${details.players}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Requirements: ${details.requirements}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                WishlistButton(game = Game(appid = appid), onWishlistClick = onWishlistClick)
            }
        }
    }
}