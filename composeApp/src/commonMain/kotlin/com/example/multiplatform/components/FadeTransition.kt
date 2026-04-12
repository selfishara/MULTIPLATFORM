package com.example.multiplatform.components

/**
 * Animation wrapper that provides fade-in/fade-out transitions for screen content.
 *
 * Useful for smooth transitions between navigation states without
 * complex state management. Simply wraps the screen content.
 *
 * @param content The composable content to animate
 */
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@Composable
fun FadeTransition(
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }
}

