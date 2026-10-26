package com.event.teamapp.model

data class PlayerState(
    val data: PlayerData,
    var status: PlayerStatus = PlayerStatus.WAITING
)