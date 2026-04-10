package com.example.multiplatform.screens

/**
 * Screen displaying detailed information about a specific exercise.
 *
 * Shows the exercise details including name, target muscle group, and execution instructions.
 * Provides an option to add the exercise to the user's routine and navigate back to the
 * exercises list.
 *
 * @param exerciseId The unique identifier of the exercise to display.
 * @param onAddToRoutine Callback invoked when the user adds the exercise to their routine.
 * @param onBack Callback invoked when the user clicks the back button.
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.data.fakeExercises
import com.example.multiplatform.model.Exercise

@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onAddToRoutine: (Exercise) -> Unit,
    onBack: () -> Unit
) {
    val exercise = fakeExercises.find { it.id == exerciseId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (exercise != null) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Muscle group: ${exercise.muscleGroup}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = exercise.instructions,
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = { onAddToRoutine(exercise) }
            ) {
                Text("Add to routine")
            }
        } else {
            Text(
                text = "Exercise not found",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(onClick = onBack) {
             Text("Back")
         }
    }
}