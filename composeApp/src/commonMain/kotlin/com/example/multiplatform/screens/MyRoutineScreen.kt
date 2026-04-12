package com.example.multiplatform.screens

import androidx.compose.foundation.layout.*
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
 * the ability to remove individual exercises. Users can also edit the routine name
 * and clear the entire routine. Displays a message when no exercises have been
 * added yet.
 *
 * @param onBack Callback invoked when the user clicks the back button.
 */
@Composable
fun MyRoutineScreen(
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onEditRoutine: () -> Unit = {}
) {
    var routineName by remember { mutableStateOf(RoutineState.name) }
    val routine = RoutineState.routine
    val routineName = RoutineState.name

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🔤 Editable name
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
            Text("No exercises added yet")
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (routine.isNotEmpty()) {
            Button(
                onClick = { RoutineState.clearRoutine() }
            ) {
                Text("Clear Routine")
            }
        }
    }
}