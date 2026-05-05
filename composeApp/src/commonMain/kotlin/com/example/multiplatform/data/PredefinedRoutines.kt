package com.example.multiplatform.data

import com.example.multiplatform.model.Exercise
import com.example.multiplatform.model.MuscleGroup
import com.example.multiplatform.model.RoutineTemplate

object PredefinedRoutines {

    val all = listOf(
        RoutineTemplate("Push Day",   "STRENGTH",    listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.ARMS), 2),
        RoutineTemplate("Pull Day",   "STRENGTH",    listOf(MuscleGroup.BACK, MuscleGroup.ARMS), 3),
        RoutineTemplate("Leg Day",    "POWER",       listOf(MuscleGroup.LEGS, MuscleGroup.CORE), 3),
        RoutineTemplate("Full Body",  "ENDURANCE",   listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.CORE), 1),
        RoutineTemplate("Upper Body", "HYPERTROPHY", listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS), 2),
        RoutineTemplate("Core Blast", "STABILITY",   listOf(MuscleGroup.CORE, MuscleGroup.ARMS), 3),
    )

    fun resolve(template: RoutineTemplate, allExercises: List<Exercise>): List<Exercise> =
        template.groups
            .flatMap { group -> allExercises.filter { it.muscleGroup == group }.take(template.exercisesPerGroup) }
            .distinctBy { it.id }
}
