package com.event.teamapp.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
    val name: String,
    val nickname: String,
    val region: Int,
    val imageFileName: String
)