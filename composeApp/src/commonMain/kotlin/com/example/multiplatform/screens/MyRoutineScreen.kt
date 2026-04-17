package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.ExerciseItem
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.state.RoutineState

@Composable
fun MyRoutineScreen(
    onBack: () -> Unit,
    onStartWorkout: () -> Unit
) {
    var routineName by remember { mutableStateOf(RoutineState.name) }
    val routine = RoutineState.routine

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "My Routine",
            subtitle = "Keep your workout ready",
            showBackIcon = true,
            onBackClick = onBack
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = routineName,
                            onValueChange = {
                                routineName = it
                                RoutineState.renameRoutine(it)
                            },
                            label = { Text("Routine name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "${routine.size} exercises saved",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Button(
                            onClick = onStartWorkout,
                            enabled = routine.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start workout")
                        }
                    }
                }
            }

            if (routine.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Your routine is empty",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Add a few exercises from Explore and build your next session.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "Saved exercises",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(routine, key = { it.id }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onRemove = {
                            RoutineState.removeExercise(exercise)
                        }
                    )
                }

                item {
                    OutlinedButton(
                        onClick = { RoutineState.clearRoutine() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Clear routine")
                    }
                }
            }
        }
    }
}
