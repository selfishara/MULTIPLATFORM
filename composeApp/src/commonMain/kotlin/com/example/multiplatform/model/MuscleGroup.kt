package com.example.multiplatform.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class MuscleGroup(
    val displayName: String,
    val icon: ImageVector
) {
    CHEST("Chest", Icons.Filled.Favorite),
    LEGS("Legs", Icons.Filled.Add),
    BACK("Back", Icons.Filled.Star),
    SHOULDERS("Shoulders", Icons.Filled.Search),
    CORE("Core", Icons.Filled.Done),
    ARMS("Arms", Icons.Filled.FavoriteBorder);

    companion object {
        fun fromString(value: String): MuscleGroup =
            entries.find { it.displayName == value } ?: CHEST
    }
}
