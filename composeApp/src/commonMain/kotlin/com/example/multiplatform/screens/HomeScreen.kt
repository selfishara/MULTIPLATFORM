package com.example.multiplatform.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.TopBar

/**
 * Main screen of GymSpot Lite.
 *
 * From here, the user can start exploring exercise categories
 * or navigate to the current routine.
 *
 * @param onStartClick Action to navigate to the categories screen.
 * @param onNavigateToRoutine Action to navigate to the my routine screen.
 */
@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "GymSpot",
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Explore exercises and build your workout routine",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onStartClick) {
                Text("Explore")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToRoutine) {
                Text("View My Routine")
            }
        }
    }
}