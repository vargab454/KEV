package com.event.teamapp.view.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState
import com.event.teamapp.model.PlayerStatus
import kotlin.math.roundToInt

@Composable
fun AnimatedPlayerCard(player: PlayerState, targetOffset: Offset, onSlideFinished: () -> Unit) {
    val isSliding = player.status == PlayerStatus.SLIDING

    val animOffset by animateOffsetAsState(
        targetValue = if (isSliding) targetOffset else Offset(1920f / 2 - 150f, 1080f / 2 - 200f),
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        finishedListener = { if (isSliding) onSlideFinished() }
    )

    val animScale by animateFloatAsState(
        targetValue = if (isSliding) 0.0f else 1.0f,
        animationSpec = tween(800)
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(animOffset.x.roundToInt(), animOffset.y.roundToInt()) }
            .size(300.dp * animScale, 400.dp * animScale)
            .alpha(animScale)
    ) {
        if (animScale > 0.1f) {
            PlayerCard(player)
        }
    }
}