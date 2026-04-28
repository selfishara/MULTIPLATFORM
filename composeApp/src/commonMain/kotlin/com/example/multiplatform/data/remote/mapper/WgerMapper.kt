package com.example.multiplatform.data.remote.mapper

import com.example.multiplatform.data.remote.dto.WgerExerciseDto
import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.ExerciseLanguage
import com.example.multiplatform.model.MuscleGroup

object WgerMapper {

    fun toDomain(
        dto: WgerExerciseDto,
        preferredLanguage: ExerciseLanguage
    ): Exercise {
        val selectedTranslation = dto.translations.first {
            it.language == preferredLanguage.wgerLanguageId
        }

        return Exercise(
            id = dto.id.toString(),
            name = cleanText(selectedTranslation.name),
            instructions = cleanText(selectedTranslation.description),
            muscleGroup = mapMuscleGroup(dto)
        )
    }

    fun hasCompleteTranslation(
        dto: WgerExerciseDto,
        preferredLanguage: ExerciseLanguage
    ): Boolean {
        val translation = dto.translations.firstOrNull {
            it.language == preferredLanguage.wgerLanguageId
        }

        return !translation?.name.isNullOrBlank() &&
                !translation?.description.isNullOrBlank()
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

    private fun cleanText(text: String?): String {
        return text
            ?.replace(Regex("<[^>]*>"), " ")
            ?.replace("&nbsp;", " ")
            ?.replace("&amp;", "&")
            ?.replace(Regex("\\s+"), " ")
            ?.trim()
            .orEmpty()
    }

    private fun String?.containsAny(vararg values: String): Boolean {
        if (this.isNullOrBlank()) return false
        return values.any { value -> this.contains(value) }
    }
}