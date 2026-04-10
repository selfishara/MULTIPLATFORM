package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.ExerciseItem
import com.example.multiplatform.state.RoutineState

/**
 * Screen displaying the user's current workout routine.
 *
 * Shows a list of exercises that have been added to the user's routine with
 * the ability to remove individual exercises. Users can also edit the routine name,
 * clear the entire routine, and start a workout session. Displays a message when
 * no exercises have been added yet.
 *
 * @param onBack Callback invoked when the user clicks the back button.
 * @param onStartWorkout Callback invoked when the user starts a workout session.
 */
@Composable
fun MyRoutineScreen(
    onBack: () -> Unit,
    onStartWorkout: () -> Unit
) {
    var routineName by remember { mutableStateOf(RoutineState.name) }
    val routine = RoutineState.routine

    LaunchedEffect(RoutineState.name) {
        routineName = RoutineState.name
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Routine",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = routineName,
            onValueChange = {
                routineName = it
                RoutineState.renameRoutine(it)
            },
            label = { Text("Routine name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (routine.isEmpty()) {
            Text(
                text = "No exercises added yet",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Workout")
            }
        }
    }
}