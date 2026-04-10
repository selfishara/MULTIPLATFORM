package com.example.multiplatform.screens

/**
 * Screen displaying a list of available exercises.
 *
 * Shows a scrollable list of exercises with their names and target muscle groups.
 * Allows users to select an exercise to view its details or return to the previous screen.
 *
 * @param onExerciseClick Callback invoked when an exercise is selected, passing the exercise ID.
 * @param onBack Callback invoked when the user clicks the back button.
 */
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
import com.example.multiplatform.data.fakeExercises

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
            Text("Back")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fakeExercises) { exercise ->
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