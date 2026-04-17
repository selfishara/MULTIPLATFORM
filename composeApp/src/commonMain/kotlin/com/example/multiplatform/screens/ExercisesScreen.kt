package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.CategoryCard
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.data.fakeExercises
import com.example.multiplatform.model.MuscleGroup

/**
 * Categories browser screen.
 *
 * Displays all available muscle group categories as large, interactive cards.
 * Users can select a category to view exercises in that group.
 *
 * @param onCategoryClick Callback when a category is selected, passing the category name.
 * @param onBack Callback when the user clicks the back button.
 * @param onNavigateToRoutine Callback when the user clicks the routine icon in the top bar.
 */
@Composable
fun ExercisesScreen(
    onCategoryClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    val categories = MuscleGroup.entries
    val totalExercises = fakeExercises.size

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Explore",
            subtitle = "Choose a muscle group to start browsing",
            showBackIcon = true,
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "A simple way in",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "We have $totalExercises exercises ready across ${categories.size} groups.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.80f)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 170.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories, key = { it.name }) { category ->
                    val exerciseCount = fakeExercises.count { it.muscleGroup == category }
                    CategoryCard(
                        category = category,
                        exerciseCount = exerciseCount,
                        onClick = { onCategoryClick(category.displayName) }
                    )
                }
            }
        }
    }
}
