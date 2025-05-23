package com.example.simplesteamsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesteamsearch.data.api.SteamApiService
import com.example.simplesteamsearch.data.model.App
import com.example.simplesteamsearch.data.model.SteamGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<SteamGame>>(emptyList())
    val searchResults: StateFlow<List<SteamGame>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var allGames: List<App> = emptyList()

    private val apiService: SteamApiService = Retrofit.Builder()
        .baseUrl("https://api.steampowered.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SteamApiService::class.java)

    init {
        loadGames()
    }

    private fun loadGames() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val response = apiService.getAppList()
                allGames = response.applist.apps
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchGames(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val filteredGames = allGames
                    .filter { it.name.contains(query, ignoreCase = true) }
                    .take(20) // Limit to 20 results for better performance
                    .map { app ->
                        SteamGame(
                            appid = app.appid,
                            name = app.name,
                            headerImage = "https://cdn.akamai.steamstatic.com/steam/apps/${app.appid}/header.jpg",
                            price = null, // We'll need another API call to get price info
                            discount = null,
                            finalPrice = null
                        )
                    }
                
                _searchResults.value = filteredGames
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 