package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.state.RoutineState

@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    exercises: List<Exercise>,
    onAddToRoutine: (Exercise) -> Unit,
    onNavigateToRoutine: () -> Unit,
    onBack: () -> Unit
) {
    val exercise = exercises.find { it.id == exerciseId }
    val isInRoutine = RoutineState.routine.any { it.id == exerciseId }

    if (exercise == null) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (exercise != null) {
            TopBar(
                title = exercise.name,
                showBackIcon = true,
                onBackClick = onBack,
                showRoutineIcon = true,
                onRoutineIconClick = onNavigateToRoutine
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = exercise.instructions,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = {
                        if (!isInRoutine) onAddToRoutine(exercise)
                    },
                    enabled = !isInRoutine,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isInRoutine) "Added" else "Add to routine")
                }

                OutlinedButton(
                    onClick = onNavigateToRoutine,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View routine")
                }
            }
        }
    }
}