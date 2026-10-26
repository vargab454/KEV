package com.event.teamapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.event.teamapp.model.AppState
import com.event.teamapp.model.PlayerStatus
import com.event.teamapp.view.components.*
import com.event.teamapp.viewmodel.MainViewModel

@Composable
fun AppScreen(viewModel: MainViewModel) {
    val appState by viewModel.appState.collectAsState()
    val players by viewModel.players.collectAsState()
    val regionNames by viewModel.regionNames.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var editingRegion by remember { mutableStateOf<Int?>(null) }
    var newRegionName by remember { mutableStateOf("") }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(32.dp)
            )
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { viewModel.onScreenClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            for (region in 1..5) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = regionNames[region] ?: "Region $region",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.clickable {
                            newRegionName = regionNames[region] ?: ""
                            editingRegion = region
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .onGloballyPositioned { coordinates ->
                                viewModel.targetCoordinates["region_$region"] = coordinates.positionInRoot()
                            }
                    )

                    players.filter { it.data.region == region && (it.status == PlayerStatus.PLACED || it.status == PlayerStatus.DEAD) }
                        .forEach { player ->
                            RegionPlayerText(
                                player = player,
                                onClick = { viewModel.onPlayerKillClicked(player) }
                            )
                        }
                }
            }
        }

        val activePlayer = players.find { it.status == PlayerStatus.IN_CENTER || it.status == PlayerStatus.SLIDING }
        if (activePlayer != null) {
            val targetOffset = viewModel.targetCoordinates["region_${activePlayer.data.region}"] ?: Offset.Zero
            AnimatedPlayerCard(
                player = activePlayer,
                targetOffset = targetOffset,
                onSlideFinished = { viewModel.onSlideAnimationFinished(activePlayer.data.name) }
            )
        }

        if (appState == AppState.DEATH_ANIMATION && viewModel.activeDeathPlayer != null) {
            DeathOverlay(
                player = viewModel.activeDeathPlayer!!,
                onAnimationFinished = { viewModel.onDeathAnimationFinished() }
            )
        }

        if (editingRegion != null) {
            AlertDialog(
                onDismissRequest = { editingRegion = null },
                title = { Text("Rename Region") },
                text = {
                    TextField(
                        value = newRegionName,
                        onValueChange = { newRegionName = it },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.updateRegionName(editingRegion!!, newRegionName)
                        editingRegion = null
                    }) { Text("Save") }
                }
            )
        }
    }
}