package com.example.multiplatform.model

/**
 * Represents an exercise available in the application.
 *
 * @property id Unique identifier of the exercise.
 * @property name Visible name of the exercise.
 * @property muscleGroup Main muscle group targeted (categorization).
 * @property instructions Basic execution description.
 */
data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: MuscleGroup,
    val instructions: String,
)
