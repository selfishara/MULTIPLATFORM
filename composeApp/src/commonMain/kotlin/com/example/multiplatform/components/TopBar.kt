package com.example.multiplatform.components

/**
 * Top navigation bar for GymSpot Lite.
 *
 * Provides consistent navigation header across all screens with:
 * - App title on the left
 * - Routine icon on the right (when applicable)
 * - Material Design 3 styling
 *
 * @param title The title to display (e.g., "GymSpot", "Exercises", "My Routine")
 * @param showRoutineIcon Whether to show the routine icon on the right
 * @param onRoutineIconClick Callback when routine icon is clicked
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    showRoutineIcon: Boolean = false,
    onRoutineIconClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            if (showRoutineIcon) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "View My Routine",
                    modifier = Modifier
                        .clickable { onRoutineIconClick() }
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

