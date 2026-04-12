package com.example.multiplatform.components

/**
 * Reusable card component for displaying an exercise item.
 *
 * Used in My Routine screen to show added exercises with remove functionality.
 * Displays exercise name, target muscle group, and a remove button.
 *
 * @param exercise The exercise to display
 * @param onRemove Callback when remove button is clicked
 */
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.model.Exercise

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onRemove: () -> Unit
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = exercise.muscleGroup.displayName,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}