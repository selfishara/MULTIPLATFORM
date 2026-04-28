package com.example.multiplatform.data.supabase

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup

object SupabaseExerciseMapper {

    fun toDomain(dto: SupabaseExerciseDto): Exercise {
        return Exercise(
            id = dto.id,
            name = dto.name,
            instructions = dto.instructions,
            muscleGroup = MuscleGroup.fromString(dto.muscle_group)
        )
    }

    fun fromDomain(
        exercise: Exercise,
        language: String
    ): SupabaseExerciseDto {
        return SupabaseExerciseDto(
            id = "${exercise.id}_$language",
            name = exercise.name,
            instructions = exercise.instructions,
            language = language,
            muscle_group = exercise.muscleGroup.displayName,
            source = "wger"
        )
    }
}