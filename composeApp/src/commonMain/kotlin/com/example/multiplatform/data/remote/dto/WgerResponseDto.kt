package com.example.multiplatform.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WgerResponseDto(
    val results: List<WgerExerciseDto> = emptyList()
)

@Serializable
data class WgerExerciseDto(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val category: WgerCategoryDto? = null,
    val translations: List<WgerTranslationDto> = emptyList()
)

@Serializable
data class WgerCategoryDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class WgerTranslationDto(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    @SerialName("name_original")
    val nameOriginal: String? = null
)