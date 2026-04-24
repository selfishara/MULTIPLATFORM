package com.example.multiplatform.data.remote.mapper

import com.example.multiplatform.data.remote.dto.WgerExerciseDto
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup

object WgerMapper {

    fun toDomain(dto: WgerExerciseDto): Exercise {
        val bestTranslation = dto.translations.firstOrNull {
            !it.name.isNullOrBlank() || !it.description.isNullOrBlank()
        }

        val resolvedName =
            bestTranslation?.name?.takeIf { it.isNotBlank() }
                ?: bestTranslation?.nameOriginal?.takeIf { it.isNotBlank() }
                ?: dto.name?.takeIf { it.isNotBlank() }
                ?: "Unnamed exercise"

        val resolvedDescription =
            bestTranslation?.description?.takeIf { it.isNotBlank() }
                ?: dto.description?.takeIf { it.isNotBlank() }
                ?: "No instructions available."

        return Exercise(
            id = dto.id.toString(),
            name = resolvedName,
            instructions = cleanDescription(resolvedDescription),
            muscleGroup = mapMuscleGroup(dto)
        )
    }

    private fun mapMuscleGroup(dto: WgerExerciseDto): MuscleGroup {
        val categoryName = dto.category?.name?.trim()?.lowercase()

        return when {
            categoryName.containsAny("chest", "pectorals", "pectoral") -> MuscleGroup.CHEST
            categoryName.containsAny("leg", "legs", "quadriceps", "hamstrings", "calves", "glutes") -> MuscleGroup.LEGS
            categoryName.containsAny("back", "lats", "traps", "spine") -> MuscleGroup.BACK
            categoryName.containsAny("shoulder", "shoulders", "deltoid", "deltoids") -> MuscleGroup.SHOULDERS
            categoryName.containsAny("arm", "arms", "biceps", "triceps", "forearms") -> MuscleGroup.ARMS
            categoryName.containsAny("abs", "abdominals", "core") -> MuscleGroup.CORE
            else -> MuscleGroup.CORE
        }
    }

    private fun cleanDescription(description: String?): String {
        return description
            ?.replace(Regex("<[^>]*>"), " ")
            ?.replace("&nbsp;", " ")
            ?.replace("&amp;", "&")
            ?.replace(Regex("\\s+"), " ")
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: "No instructions available."
    }

    private fun String?.containsAny(vararg values: String): Boolean {
        if (this.isNullOrBlank()) return false
        return values.any { value -> this.contains(value) }
    }
}