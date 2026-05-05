package com.example.multiplatform.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.multiplatform.data.PredefinedRoutines
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage
import com.example.multiplatform.model.MuscleGroup
import com.example.multiplatform.model.RoutineTemplate

private val cardScrim = Brush.verticalGradient(
    0f to Color(0x11000000),
    0.5f to Color(0x77000000),
    1f to Color(0xEE000000),
)

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onNavigateToRoutine: () -> Unit,
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    selectedLanguage: ExerciseLanguage,
    onLanguageChange: (ExerciseLanguage) -> Unit,
    exercises: List<Exercise> = emptyList(),
    onApplyTemplate: (RoutineTemplate) -> Unit = {},
    isLoading: Boolean = false,
    exerciseCount: Int = 0,
    syncError: String? = null
) {
    val totalGroups = MuscleGroup.entries.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // ─── HERO ────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            AsyncImage(
                model = MuscleGroup.CHEST.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(modifier = Modifier.matchParentSize().background(cardScrim))

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primary.copy(0.18f)
                ) {
                    Text(
                        text = "GYMSPOT LITE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }

                Text(
                    text = "Train\nHard.",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        StatPill("EXERCISES", exerciseCount.toString())
                    }
                    StatPill("GROUPS", totalGroups.toString())
                    Spacer(Modifier.weight(1f))
                    LangChip("EN", selectedLanguage == ExerciseLanguage.ENGLISH) { onLanguageChange(ExerciseLanguage.ENGLISH) }
                    LangChip("ES", selectedLanguage == ExerciseLanguage.SPANISH) { onLanguageChange(ExerciseLanguage.SPANISH) }
                }
            }
        }

        // ─── ACTIONS ─────────────────────────────────────────────
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Browse")
                }
                OutlinedButton(
                    onClick = onNavigateToRoutine,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("My Routine")
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onNavigateToFavorites,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Favorites")
                }
                OutlinedButton(
                    onClick = onNavigateToHistory,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("History")
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Profile")
                }
                OutlinedButton(
                    onClick = onNavigateToSettings,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Settings")
                }
            }

            if (syncError != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        text = syncError,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
            }
        }

        // ─── QUICK START ─────────────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quick Start",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (exercises.isEmpty() && !isLoading) {
                    Text(
                        text = "Load exercises first",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(PredefinedRoutines.all, key = { it.name }) { template ->
                    TemplateCard(
                        template = template,
                        enabled = exercises.isNotEmpty(),
                        onClick = { onApplyTemplate(template) }
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

// ─── PRIVATE COMPONENTS ──────────────────────────────────────────

@Composable
private fun TemplateCard(template: RoutineTemplate, enabled: Boolean, onClick: () -> Unit) {
    Card(
        onClick = { if (enabled) onClick() },
        modifier = Modifier.width(170.dp).height(110.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = template.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(modifier = Modifier.matchParentSize().background(cardScrim))
            if (!enabled) {
                Box(modifier = Modifier.matchParentSize().background(Color(0x88000000)))
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = template.tag,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = template.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun StatPill(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = Color.White.copy(alpha = 0.12f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(0.7f))
        }
    }
}

@Composable
private fun LangChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !selected,
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.White.copy(0.25f) else Color.White.copy(0.10f),
            contentColor = Color.White,
            disabledContainerColor = Color.White.copy(0.25f),
            disabledContentColor = Color.White,
        ),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}
