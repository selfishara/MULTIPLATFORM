package com.example.multiplatform.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Colors = darkColorScheme(
    primary = Color(0xFFFF5000),          onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF3A1600), onPrimaryContainer = Color(0xFFFFB59A),
    secondary = Color(0xFF9AA0A8),        onSecondary = Color(0xFF0F0F0F),
    secondaryContainer = Color(0xFF28282A), onSecondaryContainer = Color(0xFFD8DDE3),
    background = Color(0xFF0F0F0F),       onBackground = Color(0xFFFAFAFA),
    surface = Color(0xFF1A1A1A),          onSurface = Color(0xFFFAFAFA),
    surfaceVariant = Color(0xFF252528),   onSurfaceVariant = Color(0xFF707072),
    outline = Color(0xFF3C3C3E),
    error = Color(0xFFD30005),            onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF3B0000),   onErrorContainer = Color(0xFFFFB3AE),
)

private val GymShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

private fun ts(weight: FontWeight, size: Int, line: Int, tracking: Float = 0f) =
    TextStyle(fontWeight = weight, fontSize = size.sp, lineHeight = line.sp, letterSpacing = tracking.sp)

private val GymTypography = Typography(
    displayLarge  = ts(FontWeight.ExtraBold, 32, 40),
    headlineMedium = ts(FontWeight.Bold,      24, 32),
    titleLarge    = ts(FontWeight.SemiBold,   20, 28),
    titleMedium   = ts(FontWeight.Medium,     16, 24, 0.15f),
    bodyLarge     = ts(FontWeight.Normal,     16, 24, 0.5f),
    bodyMedium    = ts(FontWeight.Normal,     14, 20, 0.25f),
    labelLarge    = ts(FontWeight.Medium,     14, 20, 0.1f),
    labelSmall    = ts(FontWeight.Medium,     11, 16, 0.5f),
)

@Composable
fun GymSpotTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, shapes = GymShapes, typography = GymTypography, content = content)
}
