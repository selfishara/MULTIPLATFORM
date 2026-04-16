package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.CategoryCard
import com.example.multiplatform.components.TopBar
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "Categories",
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine,
            showBackIcon = true,
            onBackClick = onBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.displayName) }
                )
            }
        }
    }
}