package com.example.multiplatform.model

data class RoutineTemplate(
    val name: String,
    val tag: String,
    val groups: List<MuscleGroup>,
    val exercisesPerGroup: Int = 3,
) {
    val coverUrl: String get() = groups.first().coverUrl
}
