package com.example.multiplatform.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.state.RoutineState
import com.example.multiplatform.state.WorkoutSessionState
import kotlinx.coroutines.delay

@Composable
fun WorkoutScreen(onBack: () -> Unit) {
    val routine = RoutineState.routine

    LaunchedEffect(Unit) { WorkoutSessionState.start(routine) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            WorkoutSessionState.tickSecond()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (routine.isNotEmpty() && !WorkoutSessionState.isFinished) {
            LinearProgressIndicator(
                progress = { WorkoutSessionState.progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        TopBar(
            title = if (WorkoutSessionState.isFinished) "Complete!" else "Workout",
            showBackIcon = true,
            onBackClick = onBack
        )

        when {
            routine.isEmpty() -> EmptyRoutineCard()
            WorkoutSessionState.isFinished -> WorkoutSummary(onBack = onBack)
            else -> ActiveWorkout()
        }
    }
}

@Composable
private fun ActiveWorkout() {
    val exercise = WorkoutSessionState.currentExercise ?: return
    val setsCompleted = WorkoutSessionState.setsCompleted
    val allSetsDone = WorkoutSessionState.allSetsForCurrentDone
    val isResting = WorkoutSessionState.isResting
    val restLeft = WorkoutSessionState.restSecondsLeft
    val currentIndex = WorkoutSessionState.currentIndex
    val total = WorkoutSessionState.exercises.size
    val isLastExercise = currentIndex == total - 1

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Image header
            Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                AsyncImage(
                    model = exercise.muscleGroup.coverUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                Box(
                    modifier = Modifier.matchParentSize().background(
                        Brush.verticalGradient(
                            0.3f to Color.Transparent,
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
                )
                Text(
                    text = "${currentIndex + 1} / $total",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp, bottom = 160.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primary.copy(0.15f)
                ) {
                    Text(
                        text = exercise.muscleGroup.displayName.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Sets dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SETS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    for (i in 0 until WorkoutSessionState.targetSets) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = if (i < setsCompleted) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        )
                    }
                    Text(
                        text = "$setsCompleted / ${WorkoutSessionState.targetSets}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${WorkoutSessionState.targetReps} REPS",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Rest timer
                if (isResting) {
                    val m = restLeft / 60
                    val s = restLeft % 60
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "REST",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 4.sp
                        )
                        Text(
                            text = "$m:${s.toString().padStart(2, '0')}",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                if (exercise.instructions.isNotBlank()) {
                    Text(
                        text = exercise.instructions,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Pinned bottom action
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.35f to MaterialTheme.colorScheme.background
                    )
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when {
                isResting -> OutlinedButton(
                    onClick = { WorkoutSessionState.skipRest() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Skip rest")
                }

                allSetsDone -> Button(
                    onClick = { WorkoutSessionState.nextExercise() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(if (isLastExercise) "Finish workout" else "Next exercise")
                }

                else -> Button(
                    onClick = { WorkoutSessionState.completeSet() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Done  ·  ${WorkoutSessionState.targetReps} reps")
                }
            }
        }
    }
}

@Composable
private fun WorkoutSummary(onBack: () -> Unit) {
    val totalSeconds = WorkoutSessionState.totalSeconds
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    val timeStr = "$m:${s.toString().padStart(2, '0')}"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Workout Complete!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                SummaryStatColumn(label = "Time", value = timeStr)
                SummaryStatColumn(
                    label = "Exercises",
                    value = WorkoutSessionState.exercises.size.toString()
                )
                SummaryStatColumn(
                    label = "Sets",
                    value = WorkoutSessionState.totalSetsCompleted.toString()
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text("Back to routine")
            }
        }
    }
}

@Composable
private fun SummaryStatColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyRoutineCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Your routine is empty",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Add exercises from Explore before starting a workout.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
