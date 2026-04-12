package com.example.multiplatform.data

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup

/**
 * Sample exercise data for MVP testing and development.
 *
 * Contains a hardcoded list of exercises with their details including name,
 * target muscle group, and execution instructions. This is temporary data
 * for the MVP phase and will be replaced with real data from a backend
 * service or database in future versions.
 */
val fakeExercises = listOf(
    // Chest
    Exercise(
        id = "1",
        name = "Push Up",
        muscleGroup = MuscleGroup.CHEST,
        instructions = "Keep your body straight and lower yourself until your chest nearly touches the floor."
    ),
    Exercise(
        id = "4",
        name = "Bench Press",
        muscleGroup = MuscleGroup.CHEST,
        instructions = "Press the bar from chest level to full arm extension."
    ),

    // Legs
    Exercise(
        id = "2",
        name = "Squat",
        muscleGroup = MuscleGroup.LEGS,
        instructions = "Lower your hips back and down while keeping your chest up."
    ),
    Exercise(
        id = "5",
        name = "Lunges",
        muscleGroup = MuscleGroup.LEGS,
        instructions = "Step forward and lower your back knee toward the ground."
    ),

    // Core
    Exercise(
        id = "3",
        name = "Plank",
        muscleGroup = MuscleGroup.CORE,
        instructions = "Hold a straight position, engaging your core."
    ),
    Exercise(
        id = "6",
        name = "Crunches",
        muscleGroup = MuscleGroup.CORE,
        instructions = "Lie on your back and bring your chest toward your knees."
    ),

    // Back
    Exercise(
        id = "7",
        name = "Pull Up",
        muscleGroup = MuscleGroup.BACK,
        instructions = "Hang from a bar and pull your body up until your chin passes the bar."
    ),

    // Shoulders
    Exercise(
        id = "8",
        name = "Shoulder Press",
        muscleGroup = MuscleGroup.SHOULDERS,
        instructions = "Press the bar from shoulder height to full arm extension overhead."
    ),

    // Arms
    Exercise(
        id = "9",
        name = "Bicep Curls",
        muscleGroup = MuscleGroup.ARMS,
        instructions = "Curl the dumbbells from hip height to shoulder height."
    )
)