package com.event.teamapp.view.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState

@Composable
fun DeathOverlay(player: PlayerState, onAnimationFinished: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f,
        animationSpec = tween(durationMillis = 2000, delayMillis = 1000),
        finishedListener = { onAnimationFinished() }
    )

    val scaleXAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f * alphaAnim)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(300.dp, 400.dp).alpha(alphaAnim)) {
            PlayerCard(player)

            Canvas(modifier = Modifier.fillMaxSize().padding(32.dp)) {
                val strokeWidth = 16.dp.toPx() * scaleXAnim
                val color = Color.Red.copy(alpha = alphaAnim)

                drawLine(color, Offset(0f, 0f), Offset(size.width, size.height), strokeWidth = strokeWidth)
                drawLine(color, Offset(size.width, 0f), Offset(0f, size.height), strokeWidth = strokeWidth)
            }
        }
    }
}