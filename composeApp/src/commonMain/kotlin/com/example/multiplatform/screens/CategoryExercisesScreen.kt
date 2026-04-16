package com.example.multiplatform.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.data.fakeExercises
import com.example.multiplatform.model.MuscleGroup

/**
 * Screen displaying all exercises in a specific muscle group category.
 *
 * Shows a list of exercises for the selected category. Users can click on an exercise
 * to view its details and add it to their routine. No direct "Add" button here -
 * all additions happen from the ExerciseDetailScreen.
 *
 * @param categoryName The name of the selected muscle group category.
 * @param onExerciseClick Callback when an exercise is selected, passing the exercise ID.
 * @param onBack Callback when the user clicks the back button.
 * @param onNavigateToRoutine Callback when the user clicks the routine icon in the top bar.
 */
@Composable
fun CategoryExercisesScreen(
    categoryName: String,
    onExerciseClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    val category = MuscleGroup.entries.find { it.displayName == categoryName }
    val exercisesInCategory = if (category != null) {
        fakeExercises.filter { it.muscleGroup == category }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = categoryName,
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine,
            showBackIcon = true,
            onBackClick = onBack
        )

        if (exercisesInCategory.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No exercises in this category yet",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(exercisesInCategory) { exercise ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExerciseClick(exercise.id) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = exercise.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = exercise.muscleGroup.displayName,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}