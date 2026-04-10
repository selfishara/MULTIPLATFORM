package com.example.multiplatform.data

import com.example.multiplatform.model.Exercise

/**
 * Sample exercise data for MVP testing and development.
 *
 * Contains a hardcoded list of exercises with their details including name,
 * target muscle group, and execution instructions. This is temporary data
 * for the MVP phase and will be replaced with real data from a backend
 * service or database in future versions.
 */
val fakeExercises = listOf(
    Exercise(
        id = "1",
        name = "Push Up",
        muscleGroup = "Chest",
        instructions = "Keep your body straight and lower yourself until your chest nearly touches the floor."
    ),
    Exercise(
        id = "2",
        name = "Squat",
        muscleGroup = "Legs",
        instructions = "Lower your hips back and down while keeping your chest up."
    ),
    Exercise(
        id = "3",
        name = "Plank",
        muscleGroup = "Core",
        instructions = "Hold a straight position, engaging your core."
    )
)