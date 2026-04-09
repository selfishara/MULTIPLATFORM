package com.example.multiplatform.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ExerciseUi(
    val id: String,
    val name: String,
    val muscleGroup: String
)

private val sampleExercises = listOf(
    ExerciseUi("1", "Push Up", "Chest"),
    ExerciseUi("2", "Squat", "Legs"),
    ExerciseUi("3", "Plank", "Core")
)

@Composable
fun ExercisesScreen(
    onExerciseClick: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Exercises",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = onBack) {
            Text("Volver")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleExercises) { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onExerciseClick(exercise.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = exercise.muscleGroup,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}