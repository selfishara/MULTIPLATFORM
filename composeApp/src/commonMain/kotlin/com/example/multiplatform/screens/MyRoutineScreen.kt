package com.example.multiplatform.screens

/**
 * Screen displaying the user's current workout routine.
 *
 * Shows a list of exercises that have been added to the user's routine with
 * the ability to remove individual exercises. Displays a message when no
 * exercises have been added yet.
 *
 * @param onBack Callback invoked when the user clicks the back button.
 */
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatform.state.RoutineState

@Composable
fun MyRoutineScreen(
    onBack: () -> Unit
) {
    val routine = RoutineState.routine

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Routine",
            style = MaterialTheme.typography.headlineMedium
        )

         Button(onClick = onBack) {
             Text("Back")
         }

         if (routine.isEmpty()) {
             Text("No exercises added yet")
         } else {
             LazyColumn(
                 verticalArrangement = Arrangement.spacedBy(8.dp)
             ) {
                 items(routine) { exercise ->
                     Card {
                         Column(modifier = Modifier.padding(16.dp)) {
                             Column(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .weight(1f)
                             ) {
                                 Text(
                                     text = exercise.name,
                                     style = MaterialTheme.typography.titleMedium
                                 )
                                 Text(
                                     text = exercise.muscleGroup,
                                     style = MaterialTheme.typography.bodyMedium
                                 )
                             }
                             Button(
                                 onClick = { RoutineState.removeExercise(exercise) },
                                 modifier = Modifier.align(Alignment.End)
                             ) {
                                 Text("Remove")
                             }
                         }
                     }
                 }
             }
         }
    }
}