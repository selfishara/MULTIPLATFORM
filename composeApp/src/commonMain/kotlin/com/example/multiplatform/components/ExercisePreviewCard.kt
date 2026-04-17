package com.example.multiplatform.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup

@Composable
fun ExercisePreviewCard(
    exercise: Exercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = exerciseAccent(exercise.muscleGroup)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = accent.copy(alpha = 0.16f),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = exercise.muscleGroup.displayName.take(1),
                        style = MaterialTheme.typography.titleLarge,
                        color = accent
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = exercise.muscleGroup.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = accent
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = exercise.instructions,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            Text(
                text = "View",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun exerciseAccent(muscleGroup: MuscleGroup): Color {
    return when (muscleGroup) {
        MuscleGroup.CHEST -> MaterialTheme.colorScheme.primary
        MuscleGroup.LEGS -> MaterialTheme.colorScheme.secondary
        MuscleGroup.BACK -> MaterialTheme.colorScheme.tertiary
        MuscleGroup.SHOULDERS -> MaterialTheme.colorScheme.error
        MuscleGroup.CORE -> MaterialTheme.colorScheme.primary
        MuscleGroup.ARMS -> MaterialTheme.colorScheme.secondary
    }
}
