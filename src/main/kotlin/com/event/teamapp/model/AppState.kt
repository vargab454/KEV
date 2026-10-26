package com.event.teamapp.model

enum class AppState {
    DISTRIBUTION_WAITING_FOR_CLICK, // Waiting to show next player in center
    DISTRIBUTION_CENTER_IDLE,       // Player is in center, waiting for click to slide
    DISTRIBUTION_SLIDING,           // Player is actively sliding
    GAMEPLAY_IDLE,                  // All players placed, waiting for death clicks
    DEATH_ANIMATION                 // Red X and fade animation playing
}