package com.event.teamapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.PlayerState
import org.jetbrains.skia.Image
import java.io.File

@Composable
fun PlayerCard(player: PlayerState) {
    val imageFile = File("pictures/${player.data.imageFileName}")
    val goldAccent = Color(0xFFD4AF37)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF121212))
            .border(2.dp, goldAccent, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (imageFile.exists()) {
            val bitmap: ImageBitmap = Image.makeFromEncoded(imageFile.readBytes()).toComposeImageBitmap()
            androidx.compose.foundation.Image(
                bitmap = bitmap,
                contentDescription = player.data.name,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text("Missing Image:\n${player.data.imageFileName}", color = Color.Red, textAlign = TextAlign.Center)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.85f))
                .border(1.dp, goldAccent.copy(alpha = 0.5f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = player.data.name.uppercase(),
                    color = goldAccent,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "\"${player.data.nickname}\"",
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}