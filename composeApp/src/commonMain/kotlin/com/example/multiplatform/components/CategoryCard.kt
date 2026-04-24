package com.example.multiplatform.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multiplatform.model.MuscleGroup

@Composable
fun CategoryCard(
    category: MuscleGroup,
    onClick: () -> Unit
) {
    val accent = categoryAccent(category)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 160.dp, height = 160.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = accent.copy(alpha = 0.16f),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = category.displayName.take(1),
                        style = MaterialTheme.typography.titleLarge,
                        color = accent
                    )
                }
            }

            Text(
                text = category.displayName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun categoryAccent(category: MuscleGroup): Color {
    return when (category) {
        MuscleGroup.CHEST -> MaterialTheme.colorScheme.primary
        MuscleGroup.LEGS -> MaterialTheme.colorScheme.secondary
        MuscleGroup.BACK -> MaterialTheme.colorScheme.tertiary
        MuscleGroup.SHOULDERS -> MaterialTheme.colorScheme.error
        MuscleGroup.CORE -> MaterialTheme.colorScheme.primary
        MuscleGroup.ARMS -> MaterialTheme.colorScheme.secondary
    }
}