package com.example.multiplatform.components

/**
 * Top navigation bar for GymSpot Lite.
 *
 * Provides consistent navigation header across all screens with:
 * - Optional back icon on the left
 * - Title in the center
 * - Optional routine icon on the right
 * - Material Design 3 styling
 *
 * @param title The title to display.
 * @param showRoutineIcon Whether to show the routine icon on the right.
 * @param onRoutineIconClick Callback when routine icon is clicked.
 * @param showBackIcon Whether to show the back icon on the left.
 * @param onBackClick Callback when back icon is clicked.
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
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
    onRoutineIconClick: () -> Unit = {},
    showBackIcon: Boolean = false,
    onBackClick: () -> Unit = {}
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackIcon) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (showBackIcon) 8.dp else 0.dp)
            )

            if (showRoutineIcon) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
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