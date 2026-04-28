package com.example.multiplatform.model

enum class ExerciseLanguage(
    val displayName: String,
    val wgerLanguageId: Int,
    val apiCode: String
) {
    ENGLISH(
        displayName = "English",
        wgerLanguageId = 2,
        apiCode = "english"
    ),
    SPANISH(
        displayName = "Español",
        wgerLanguageId = 4,
        apiCode = "spanish"
    )
}