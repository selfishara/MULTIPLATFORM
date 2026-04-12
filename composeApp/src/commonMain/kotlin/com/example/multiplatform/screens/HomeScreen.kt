package com.example.multiplatform.screens

/**
 * Main screen of GymSpot Lite.
 *
 * Entry point where users can access the exercise list to explore and add exercises.
 * Routine icon in the top bar allows quick access to the current routine.
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
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.state.RoutineState

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "GymSpot",
            showRoutineIcon = true,
            onRoutineIconClick = onNavigateToRoutine
        )

         Text(
             text = "Explore exercises and build your workout routine",
             style = MaterialTheme.typography.bodyLarge
         )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onStartClick) {
                Text("Explore")
            }
        }
    }
}