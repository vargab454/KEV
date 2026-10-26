package com.event.teamapp.view.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState
import com.event.teamapp.model.PlayerStatus
import org.jetbrains.skia.Image
import java.io.File
import kotlin.math.roundToInt

@Composable
fun PlayerCard(player: PlayerState) {
    val imageFile = File("pictures/${player.data.imageFileName}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (imageFile.exists()) {
            val bitmap: ImageBitmap = Image.makeFromEncoded(imageFile.readBytes()).toComposeImageBitmap()
            androidx.compose.foundation.Image(bitmap = bitmap, contentDescription = player.data.name, modifier = Modifier.fillMaxSize())
        } else {
            Text("Missing Image:\n${player.data.imageFileName}", color = Color.Red)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = player.data.name,
                    color = Color.White,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = "\"${player.data.nickname}\"",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}