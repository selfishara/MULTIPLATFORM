package com.example.multiplatform.screens

/**
 * Screen displaying a list of available exercises.
 *
 * Shows a scrollable list of exercises with their names and target muscle groups.
 * Users can either click on an exercise to view its full details, or add it directly to their routine
 * using the "Add" button. This dual-path approach provides flexibility: quick addition via the button,
 * or detailed view via navigation for more information before adding.
 *
 * @param onExerciseClick Callback invoked when an exercise is selected, passing the exercise ID for detailed view.
 * @param onAddToRoutine Callback invoked when the user adds an exercise directly from the list.
 * @param onBack Callback invoked when the user clicks the back button.
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.multiplatform.model.Exercise

@Composable
fun ExercisesScreen(
    onExerciseClick: (String) -> Unit,
    onAddToRoutine: (Exercise) -> Unit,
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
                        .fillMaxWidth()
                        .clickable { onExerciseClick(exercise.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = exercise.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = exercise.muscleGroup,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onAddToRoutine(exercise) },
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
    }
}