package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.ExerciseItem
import com.example.multiplatform.state.RoutineState

/**
 * Screen displaying the user's current workout routine.
 *
 * Shows a list of exercises that have been added to the user's routine with the ability
 * to remove individual exercises. Users can edit the routine name, clear the entire routine,
 * and start a workout session. Displays a message when no exercises have been added yet.
 *
 * @param onBack Callback invoked when the user clicks the back button (via TopBar).
 * @param onStartWorkout Callback invoked when the user starts a workout session.
 * @param onEditRoutine Callback invoked when the user clicks the edit routine button.
 */
@Composable
fun MyRoutineScreen(
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onEditRoutine: () -> Unit = {}
) {
    val routine = RoutineState.routine
    val routineName = RoutineState.name

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar with back button and edit icon
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "My Routine",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                IconButton(onClick = onEditRoutine) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit routine",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Routine name display
            Text(
                text = routineName,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (routine.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No exercises added yet",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Tap Explore to start building your routine",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(routine) { exercise ->
                        ExerciseItem(
                            exercise = exercise,
                            onRemove = {
                                RoutineState.removeExercise(exercise)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { RoutineState.clearRoutine() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Routine")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onStartWorkout,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = routine.isNotEmpty()
                ) {
                    Text("Start Workout")
                }
            }
        }
    }
}