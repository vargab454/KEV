package com.event.teamapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState
import com.event.teamapp.model.PlayerStatus

@Composable
fun RegionPlayerText(player: PlayerState, onClick: () -> Unit) {
    val displayText = "${player.data.name} - \"${player.data.nickname}\""

    Text(
        text = displayText,
        style = MaterialTheme.typography.h6,
        color = if (player.status == PlayerStatus.DEAD) Color.Gray else Color.Black,
        textDecoration = if (player.status == PlayerStatus.DEAD) TextDecoration.LineThrough else TextDecoration.None,
        modifier = Modifier
            .padding(8.dp)
            .clickable(enabled = player.status == PlayerStatus.PLACED) { onClick() }
    )
}

