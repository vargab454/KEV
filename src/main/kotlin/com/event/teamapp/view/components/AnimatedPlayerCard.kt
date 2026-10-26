package com.event.teamapp.view.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState
import com.event.teamapp.model.PlayerStatus
import kotlin.math.roundToInt

@Composable
fun AnimatedPlayerCard(
    player: PlayerState,
    targetOffset: Offset,
    screenSize: IntSize,
    onSlideFinished: () -> Unit
) {
    if (screenSize == IntSize.Zero) return

    val isSliding = player.status == PlayerStatus.SLIDING

    val cardWidthPx = with(LocalDensity.current) { 450.dp.toPx() }
    val cardHeightPx = with(LocalDensity.current) { 600.dp.toPx() }

    val centerOffset = Offset(
        x = (screenSize.width - cardWidthPx) / 2f,
        y = (screenSize.height - cardHeightPx) / 2f
    )

    val animOffset by animateOffsetAsState(
        targetValue = if (isSliding) targetOffset else centerOffset,
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
            .size(450.dp * animScale, 600.dp * animScale)
            .alpha(animScale)
    ) {
        if (animScale > 0.1f) {
            PlayerCard(player)
        }
    }
}