package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.ExercisePreviewCard
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup

/**
 * Screen displaying all exercises in a specific muscle group category.
 *
 * Shows a list of exercises for the selected category. Users can click on an exercise
 * to view its details. No direct "Add" button here - all additions happen from the
 * ExerciseDetailScreen.
 *
 * @param categoryName The name of the selected muscle group category.
 * @param exercises The full list of exercises available in the app.
 * @param onExerciseClick Callback when an exercise is selected, passing the exercise ID.
 * @param onBack Callback when the user clicks the back button.
 * @param onNavigateToRoutine Callback when the user clicks the routine icon in the top bar.
 */
@Composable
fun CategoryExercisesScreen(
    categoryName: String,
    exercises: List<Exercise>,
    onExerciseClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    val category = MuscleGroup.entries.find { it.displayName == categoryName }
    val exercisesInCategory = exercises.filter { it.muscleGroup == category }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = categoryName,
            showBackIcon = true,
            onBackClick = onBack,
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine
        )

        if (exercisesInCategory.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
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
                            text = "No exercises yet",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "This category will fill out as the library grows.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(exercisesInCategory, key = { it.id }) { exercise ->
                    ExercisePreviewCard(
                        exercise = exercise,
                        onClick = { onExerciseClick(exercise.id) }
                    )
                }
            }
        }
    }
}