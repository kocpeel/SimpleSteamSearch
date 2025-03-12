package com.example.simplesteamsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.contains
import com.example.simplesteamsearch.data.model.Game
import com.example.simplesteamsearch.data.model.GameDetails
import com.example.simplesteamsearch.data.repository.SteamRepository
import kotlinx.coroutines.launch

class SteamViewModel : ViewModel() {
    private val repository = SteamRepository()

    private val _popularGames = MutableLiveData<List<Game>>()
    val popularGames: LiveData<List<Game>> = _popularGames

    private val _gameDetails = MutableLiveData<GameDetails>()
    val gameDetails: LiveData<GameDetails> = _gameDetails

    private val _wishlist = MutableLiveData<List<Game>>()
    val wishlist: LiveData<List<Game>> = _wishlist

    init {
        _wishlist.value = emptyList()
    }

    fun fetchPopularGames() {
        viewModelScope.launch {
            val games = repository.getPopularGames()
            _popularGames.value = games ?: emptyList()
        }
    }

    fun fetchGameDetails(appid: String) {
        viewModelScope.launch {
            val details = repository.getGameDetails(appid)
            _gameDetails.value = details
        }
    }

    fun addToWishlist(game: Game) {
        val currentList = _wishlist.value?.toMutableList() ?: mutableListOf()
        if (currentList.contains(game)) {
            currentList.remove(game)
        } else {
            currentList.add(game)
        }
        _wishlist.value = currentList
    }
}