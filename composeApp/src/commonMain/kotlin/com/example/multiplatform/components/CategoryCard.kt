package com.example.multiplatform.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multiplatform.model.MuscleGroup

@Composable
fun CategoryCard(
    category: MuscleGroup,
    exerciseCount: Int,
    onClick: () -> Unit
) {
    val accent = categoryAccent(category)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = accent.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.size(52.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = category.displayName.take(1),
                            style = MaterialTheme.typography.titleLarge,
                            color = accent
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = "$exerciseCount",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = categorySubtitle(category),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                color = accent,
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(
                    text = "Open category",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

private fun categoryAccent(category: MuscleGroup): Color {
    return when (category) {
        MuscleGroup.CHEST -> MaterialTheme.colorScheme.primary
        MuscleGroup.LEGS -> MaterialTheme.colorScheme.secondary
        MuscleGroup.BACK -> MaterialTheme.colorScheme.tertiary
        MuscleGroup.SHOULDERS -> MaterialTheme.colorScheme.error
        MuscleGroup.CORE -> MaterialTheme.colorScheme.primary
        MuscleGroup.ARMS -> MaterialTheme.colorScheme.secondary
    }
}

private fun categorySubtitle(category: MuscleGroup): String {
    return when (category) {
        MuscleGroup.CHEST -> "Push-focused work for upper body strength"
        MuscleGroup.LEGS -> "Build a solid lower-body base"
        MuscleGroup.BACK -> "Posture, pulling and upper-back control"
        MuscleGroup.SHOULDERS -> "Overhead strength and stability"
        MuscleGroup.CORE -> "Stability and control for the whole body"
        MuscleGroup.ARMS -> "Direct work for biceps and triceps"
    }
}
