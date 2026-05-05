package com.example.multiplatform.navigation

/**
 * Navigation configuration for serialization and state management.
 *
 * Configures the serializers module for all Route subtypes used in the application
 * (Home, Exercises, CategoryExercises, ExerciseDetail, MyRoutine, Workout) to enable
 * proper state restoration and navigation stack persistence.
 */
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Home::class, Route.Home.serializer())
            subclass(Route.Exercises::class, Route.Exercises.serializer())
            subclass(Route.CategoryExercises::class, Route.CategoryExercises.serializer())
            subclass(Route.ExerciseDetail::class, Route.ExerciseDetail.serializer())
            subclass(Route.MyRoutine::class, Route.MyRoutine.serializer())
            subclass(Route.Workout::class, Route.Workout.serializer())
            subclass(Route.EditRoutine::class, Route.EditRoutine.serializer())
            subclass(Route.Login::class, Route.Login.serializer())
            subclass(Route.Favorites::class, Route.Favorites.serializer())
            subclass(Route.History::class, Route.History.serializer())
            subclass(Route.Profile::class, Route.Profile.serializer())
            subclass(Route.Settings::class, Route.Settings.serializer())
        }
    }
}