package com.example.multiplatform.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents the muscle groups/categories available in the app.
 * Each exercise belongs to one of these categories.
 * Each category has an associated icon for visual identification.
 */
enum class MuscleGroup(
    val displayName: String,
    val icon: ImageVector
) {
    CHEST("Chest", Icons.Filled.FitnessCenter),
    LEGS("Legs", Icons.Filled.DirectionsRun),
    BACK("Back", Icons.Filled.FavoriteBorder),
    SHOULDERS("Shoulders", Icons.Filled.EmojiPeople),
    CORE("Core", Icons.Filled.AutoAwesome),
    ARMS("Arms", Icons.Filled.Favorite);

    companion object {
        fun fromString(value: String): MuscleGroup {
            return entries.find { it.displayName == value } ?: CHEST
        }
    }
}
