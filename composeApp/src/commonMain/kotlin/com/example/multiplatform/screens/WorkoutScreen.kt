package com.example.multiplatform.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.state.RoutineState

/**
 * Screen for executing and tracking a workout session.
 *
 * Displays exercises from the user's routine one at a time, allowing navigation through
 * the workout. Shows exercise details including name, target muscle group, and instructions,
 * as well as progress through the workout. Back navigation is handled by the TopBar.
 *
 * @param onBack Callback invoked when the user clicks the back button (via TopBar).
 */
@Composable
fun WorkoutScreen(
    onBack: () -> Unit
) {
    val routine = RoutineState.routine
    var currentExerciseIndex by remember { mutableStateOf(0) }

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
                    text = "Workout",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        if (routine.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your routine is empty",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Add exercises to start a workout",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Progress section
                val progress = (currentExerciseIndex + 1) / routine.size.toFloat()
                Text(
                    text = "Exercise ${currentExerciseIndex + 1} of ${routine.size}",
                    style = MaterialTheme.typography.labelSmall
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Exercise details
                val currentExercise = routine[currentExerciseIndex]

                Text(
                    text = currentExercise.name,
                    style = MaterialTheme.typography.displaySmall
                )

                Text(
                    text = "Muscle group: ${currentExercise.muscleGroup}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentExercise.instructions,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                // Navigation buttons
                if (currentExerciseIndex < routine.lastIndex) {
                    Button(
                        onClick = { currentExerciseIndex++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next Exercise")
                    }
                } else {
                    Text(
                        text = "✓ Workout Completed!",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Finish Workout")
                    }
                }
            }
        }
    }
}