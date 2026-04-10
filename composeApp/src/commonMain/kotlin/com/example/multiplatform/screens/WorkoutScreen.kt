package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.state.RoutineState

@Composable
fun WorkoutScreen(
    onBack: () -> Unit
){
    val routine = RoutineState.routine
    val currentExerciseIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
        .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = "Workout Mode",
            style = MaterialTheme.typography.headlineMedium
        )

        if (routine.isEmpty()) {
            Text(
                text = "Your routine is empty. Add exercises before starting a workout.",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(onClick = onBack) {
                Text("Back")
            }
        } else {
            val currentExercise = routine[currentExerciseIndex]

            Text(
                text = "Exercise ${currentExerciseIndex + 1} of ${routine.size}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = currentExercise.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Muscle group: ${currentExercise.muscleGroup}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = currentExercise.instructions,
                style = MaterialTheme.typography.bodyMedium
            )

            if (currentExerciseIndex < routine.lastIndex) {
                Button(
                    onClick = { currentExerciseIndex++ }
                ) {
                    Text("Next exercise")
                }
            } else {
                Text(
                    text = "Workout completed!",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}