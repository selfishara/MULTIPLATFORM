package com.example.multiplatform.screens

/**
 * Main screen of GymSpot Lite.
 *
 * From here, the user can access the exercise list
 * or navigate to their current routine.
 *
 * @param onStartClick Action to navigate to the exercises screen.
 * @param onNavigateToRoutine Action to navigate to the my routine screen.
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "GymSpot",
            style = MaterialTheme.typography.headlineLarge
        )

         Text(
             text = "Explore exercises and build your workout routine",
             style = MaterialTheme.typography.bodyLarge
         )

         Button(onClick = onStartClick) {
             Text("GET STARTED")
         }

         Spacer(modifier = Modifier.height(16.dp))

         Button(onClick = {
             onNavigateToRoutine()
         }) {
             Text("View My Routine")
        }
    }
}