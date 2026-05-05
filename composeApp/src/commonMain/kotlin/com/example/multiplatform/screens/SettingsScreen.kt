package com.example.multiplatform.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.multiplatform.components.TopBar
import com.example.multiplatform.model.ExerciseLanguage
import com.example.multiplatform.state.AppSettingsState

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(title = "Settings", showBackIcon = true, onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ── Language ──────────────────────────────────────────
            SettingsSection(title = "Language") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LanguageButton(
                        label = "English",
                        selected = AppSettingsState.exerciseLanguage == ExerciseLanguage.ENGLISH,
                        modifier = Modifier.weight(1f),
                        onClick = { AppSettingsState.updateExerciseLanguage(ExerciseLanguage.ENGLISH) }
                    )
                    LanguageButton(
                        label = "Español",
                        selected = AppSettingsState.exerciseLanguage == ExerciseLanguage.SPANISH,
                        modifier = Modifier.weight(1f),
                        onClick = { AppSettingsState.updateExerciseLanguage(ExerciseLanguage.SPANISH) }
                    )
                }
            }

            // ── Workout preferences ───────────────────────────────
            SettingsSection(title = "Workout") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    StepperRow(
                        label = "Sets per exercise",
                        value = AppSettingsState.targetSets,
                        unit = "sets",
                        onDecrement = { AppSettingsState.updateTargetSets(AppSettingsState.targetSets - 1) },
                        onIncrement = { AppSettingsState.updateTargetSets(AppSettingsState.targetSets + 1) },
                        canDecrement = AppSettingsState.targetSets > 1,
                        canIncrement = AppSettingsState.targetSets < 5
                    )
                    StepperRow(
                        label = "Reps per set",
                        value = AppSettingsState.targetReps,
                        unit = "reps",
                        onDecrement = { AppSettingsState.updateTargetReps(AppSettingsState.targetReps - 1) },
                        onIncrement = { AppSettingsState.updateTargetReps(AppSettingsState.targetReps + 1) },
                        canDecrement = AppSettingsState.targetReps > 5,
                        canIncrement = AppSettingsState.targetReps < 20
                    )
                    StepperRow(
                        label = "Rest between sets",
                        value = AppSettingsState.restSeconds,
                        unit = "sec",
                        onDecrement = { AppSettingsState.updateRestSeconds(AppSettingsState.restSeconds - 15) },
                        onIncrement = { AppSettingsState.updateRestSeconds(AppSettingsState.restSeconds + 15) },
                        canDecrement = AppSettingsState.restSeconds > 15,
                        canIncrement = AppSettingsState.restSeconds < 120
                    )
                }
            }

            // ── About ─────────────────────────────────────────────
            SettingsSection(title = "About") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    AboutRow(label = "App", value = "GymSpot Lite")
                    AboutRow(label = "Version", value = "1.0.0")
                    AboutRow(label = "Platform", value = "Kotlin Multiplatform")
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun LanguageButton(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !selected,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun StepperRow(
    label: String,
    value: Int,
    unit: String,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    canDecrement: Boolean,
    canIncrement: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = onDecrement,
                enabled = canDecrement,
                modifier = Modifier.size(36.dp)
            ) {
                Text(
                    text = "−",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (canDecrement) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f)
                )
            }
            Text(
                text = "$value $unit",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            IconButton(
                onClick = onIncrement,
                enabled = canIncrement,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    modifier = Modifier.size(20.dp),
                    tint = if (canIncrement) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f)
                )
            }
        }
    }
}

@Composable
private fun AboutRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
