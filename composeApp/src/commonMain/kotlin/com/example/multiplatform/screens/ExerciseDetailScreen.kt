package com.example.multiplatform.screens

/**
 * Screen displaying detailed information about a specific exercise.
 *
 * Shows exercise details including name, target muscle group, and execution instructions.
 * Provides an option to add the exercise to the user's routine. Back navigation is handled
 * by the TopBar back button, not a UI button on this screen.
 *
 * @param exerciseId The unique identifier of the exercise to display.
 * @param onAddToRoutine Callback invoked when the user adds the exercise to their routine.
 * @param onViewRoutine Callback invoked when the user wants to view their routine.
 * @param onBack Callback invoked when the user clicks the back button (via TopBar).
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar with back button
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = "Exercise Details",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (exercise != null) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.displaySmall
                )

                Text(
                    text = "Muscle group: ${exercise.muscleGroup.displayName}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = exercise.instructions,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Add button as icon
                IconButton(
                    onClick = {
                        onAddToRoutine(exercise)
                    },
                    enabled = !isInRoutine,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isInRoutine) Icons.Filled.Check else Icons.Filled.Add,
                        contentDescription = if (isInRoutine) "Added to routine" else "Add to routine",
                        tint = if (isInRoutine) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                    )
                }

                if (isInRoutine) {
                    Text(
                        text = "✓ Added to routine",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            } else {
                Text(
                    text = "Exercise not found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}