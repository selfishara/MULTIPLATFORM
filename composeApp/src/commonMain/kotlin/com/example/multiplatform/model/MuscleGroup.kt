package com.example.multiplatform.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

private const val PICSUM = "https://picsum.photos/seed"

enum class MuscleGroup(
    val displayName: String,
    val icon: ImageVector,
    val coverUrl: String,
) {
    CHEST("Chest", Icons.Filled.Favorite, "$PICSUM/gs-chest/600/400"),
    LEGS("Legs", Icons.Filled.Add, "$PICSUM/gs-legs/600/400"),
    BACK("Back", Icons.Filled.Star, "$PICSUM/gs-back/600/400"),
    SHOULDERS("Shoulders", Icons.Filled.Search, "$PICSUM/gs-shoulders/600/400"),
    CORE("Core", Icons.Filled.Done, "$PICSUM/gs-core/600/400"),
    ARMS("Arms", Icons.Filled.FavoriteBorder, "$PICSUM/gs-arms/600/400");

    companion object {
        fun fromString(value: String): MuscleGroup =
            entries.find { it.displayName == value } ?: CHEST
    }
}
