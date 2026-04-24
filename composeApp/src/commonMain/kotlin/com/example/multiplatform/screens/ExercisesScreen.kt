package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.CategoryCard
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.model.MuscleGroup

@Composable
fun ExercisesScreen(
    onCategoryClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    val categories = MuscleGroup.entries

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Explore",
            showBackIcon = true,
            onBackClick = onBack,
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories, key = { it.name }) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.displayName) }
                )
            }
        }
    }
}