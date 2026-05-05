package com.example.multiplatform.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

private const val UNSPLASH = "https://images.unsplash.com"

enum class MuscleGroup(
    val displayName: String,
    val icon: ImageVector,
    val coverUrl: String,
) {
    CHEST("Chest", Icons.Filled.Favorite,
        "$UNSPLASH/photo-1571019613454-1cb2f99b2d8b?w=600&auto=format&fit=crop"),
    LEGS("Legs", Icons.Filled.Add,
        "$UNSPLASH/photo-1517836357463-d25dfeac3438?w=600&auto=format&fit=crop"),
    BACK("Back", Icons.Filled.Star,
        "$UNSPLASH/photo-1603287681836-b174ce5074c2?w=600&auto=format&fit=crop"),
    SHOULDERS("Shoulders", Icons.Filled.Search,
        "$UNSPLASH/photo-1532029837206-abbe2b7620e3?w=600&auto=format&fit=crop"),
    CORE("Core", Icons.Filled.Done,
        "$UNSPLASH/photo-1571019614242-c5c5dee9f50b?w=600&auto=format&fit=crop"),
    ARMS("Arms", Icons.Filled.FavoriteBorder,
        "$UNSPLASH/photo-1581009137042-c552e485697a?w=600&auto=format&fit=crop");

    companion object {
        fun fromString(value: String): MuscleGroup =
            entries.find { it.displayName == value } ?: CHEST
    }
}
