package com.example.multiplatform.components

/**
 * Reusable composable for displaying an exercise item in a list or card format.
 *
 * Displays the exercise name and target muscle group with a remove button.
 * Used in MyRoutineScreen to avoid code duplication.
 *
 * @param exercise The exercise data to display.
 * @param onRemove Callback invoked when the remove button is clicked.
 */
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
                text = exercise.muscleGroup,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}