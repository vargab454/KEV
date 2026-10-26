package com.event.teamapp.model

enum class PlayerStatus {
    WAITING,       // Not yet shown
    IN_CENTER,     // Popped up in the center during distribution
    SLIDING,       // Currently animating to its region
    PLACED,        // In region, alive
    DEAD           // In region, dead (grayed out)
}