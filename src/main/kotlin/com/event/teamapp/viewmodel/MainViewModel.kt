package com.event.teamapp.viewmodel

import androidx.compose.ui.geometry.Offset
import com.event.teamapp.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import kotlinx.serialization.json.Json

class MainViewModel {
    private val _appState = MutableStateFlow(AppState.DISTRIBUTION_WAITING_FOR_CLICK)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _players = MutableStateFlow<List<PlayerState>>(emptyList())
    val players: StateFlow<List<PlayerState>> = _players.asStateFlow()

    private val _regionNames = MutableStateFlow((1..5).associateWith { "Region $it" })
    val regionNames: StateFlow<Map<Int, String>> = _regionNames.asStateFlow()

    // New state to hold our error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val targetCoordinates = mutableMapOf<String, Offset>()

    var activeDeathPlayer: PlayerState? = null

    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        val file = File("players.json")
        if (file.exists()) {
            try {
                val jsonString = file.readText()
                val parsedData = Json.decodeFromString<List<PlayerData>>(jsonString)
                _players.value = parsedData.map { PlayerState(it) }
            } catch (e: Exception) {
                // Catches syntax errors in the JSON file
                _errorMessage.value = "Error reading players.json: ${e.message}"
            }
        } else {
            // File is completely missing
            _errorMessage.value = "Critical Error: 'players.json' was not found in the application root directory."
        }
    }

    fun updateRegionName(region: Int, newName: String) {
        _regionNames.value = _regionNames.value.toMutableMap().apply { put(region, newName) }
    }

    fun onScreenClick() {
        if (_errorMessage.value != null) return // Ignore clicks if there's an error

        when (_appState.value) {
            AppState.DISTRIBUTION_WAITING_FOR_CLICK -> showNextPlayerInCenter()
            AppState.DISTRIBUTION_CENTER_IDLE -> slideCurrentPlayer()
            else -> {}
        }
    }

    private fun showNextPlayerInCenter() {
        val currentList = _players.value.toMutableList()
        val nextIndex = currentList.indexOfFirst { it.status == PlayerStatus.WAITING }

        if (nextIndex != -1) {
            currentList[nextIndex] = currentList[nextIndex].copy(status = PlayerStatus.IN_CENTER)
            _players.value = currentList
            _appState.value = AppState.DISTRIBUTION_CENTER_IDLE
        } else {
            _appState.value = AppState.GAMEPLAY_IDLE
        }
    }

    private fun slideCurrentPlayer() {
        val currentList = _players.value.toMutableList()
        val centerIndex = currentList.indexOfFirst { it.status == PlayerStatus.IN_CENTER }

        if (centerIndex != -1) {
            currentList[centerIndex] = currentList[centerIndex].copy(status = PlayerStatus.SLIDING)
            _players.value = currentList
            _appState.value = AppState.DISTRIBUTION_SLIDING
        }
    }

    fun onSlideAnimationFinished(playerName: String) {
        val currentList = _players.value.toMutableList()
        val index = currentList.indexOfFirst { it.data.name == playerName }

        if (index != -1) {
            currentList[index] = currentList[index].copy(status = PlayerStatus.PLACED)
            _players.value = currentList
            _appState.value = AppState.DISTRIBUTION_WAITING_FOR_CLICK
        }
    }

    fun onPlayerKillClicked(player: PlayerState) {
        if (_appState.value == AppState.GAMEPLAY_IDLE && player.status == PlayerStatus.PLACED) {
            activeDeathPlayer = player
            _appState.value = AppState.DEATH_ANIMATION
        }
    }

    fun onDeathAnimationFinished() {
        activeDeathPlayer?.let { deadPlayer ->
            val currentList = _players.value.toMutableList()
            val index = currentList.indexOfFirst { it.data.name == deadPlayer.data.name }
            if (index != -1) {
                currentList[index] = currentList[index].copy(status = PlayerStatus.DEAD)
                _players.value = currentList
            }
        }
        activeDeathPlayer = null
        _appState.value = AppState.GAMEPLAY_IDLE
    }
}