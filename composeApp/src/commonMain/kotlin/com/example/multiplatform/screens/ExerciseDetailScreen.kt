package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.data.fakeExercises
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.state.RoutineState

/**
 * Screen displaying detailed information about a specific exercise.
 *
 * Shows exercise details including name, target muscle group, and execution instructions.
 * Provides an option to add the exercise to the user's routine. Back navigation and
 * routine access are handled through the TopBar.
 *
 * @param exerciseId The unique identifier of the exercise to display.
 * @param onAddToRoutine Callback invoked when the user adds the exercise to their routine.
 * @param onNavigateToRoutine Callback invoked when the user clicks the routine icon.
 * @param onBack Callback invoked when the user clicks the back button.
 */
@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onAddToRoutine: (Exercise) -> Unit,
    onNavigateToRoutine: () -> Unit,
    onBack: () -> Unit
) {
    val exercise = fakeExercises.find { it.id == exerciseId }
    val isInRoutine = RoutineState.routine.any { it.id == exerciseId }

    if (exercise == null) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "Exercise",
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine,
            showBackIcon = true,
            onBackClick = onBack
        )

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
                        tint = if (isInRoutine) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
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