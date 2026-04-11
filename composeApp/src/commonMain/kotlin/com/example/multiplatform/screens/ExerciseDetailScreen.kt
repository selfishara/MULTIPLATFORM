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
 * @param onViewRoutine Callback invoked when the user wants to view their routine.
 * @param onBack Callback invoked when the user clicks the back button.
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.data.fakeExercises
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.state.RoutineState

@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onAddToRoutine: (Exercise) -> Unit,
    onViewRoutine: () -> Unit,
    onBack: () -> Unit
) {
    val exercise = fakeExercises.find { it.id == exerciseId }
    val isInRoutine = RoutineState.routine.any { it.id == exerciseId }

    // Automatically navigate back if exercise is not found
    if (exercise == null) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }

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
                onClick = {
                    onAddToRoutine(exercise)
                },
                enabled = !isInRoutine
            ) {
                Text(if(isInRoutine) "Added" else "Add to routine")
            }

            if (isInRoutine) {
                Button(onClick = onViewRoutine) {
                    Text("View My Routine")
                }
            }
        } else {
            Text(
                text = "Exercise not found",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) {
             Text("Back")
         }
    }
}