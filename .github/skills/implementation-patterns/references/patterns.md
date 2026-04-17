# GymSpot Lite Implementation Patterns Reference

This document defines the implementation patterns that should be followed in the GymSpot Lite project.

GymSpot Lite is a Kotlin Multiplatform fitness application focused on:
- exploring exercises
- browsing exercises by category
- viewing exercise details
- creating and editing workout routines
- running workout sessions

The project is currently MVP-oriented and intentionally lightweight.  
Patterns should support growth, but must avoid unnecessary overengineering.

---

## General Principles

### 1. Build incrementally
Implement features in small, vertical slices:
- route
- screen
- state
- reusable component
- user flow

Do not redesign the whole project when only one block is being worked on.

### 2. Respect the current project stage
GymSpot Lite is not yet a large production app.  
Prefer:
- simple state objects
- lightweight navigation
- reusable UI components
- explicit flows

Avoid:
- heavy dependency injection frameworks
- large abstractions too early
- unnecessary layers when the project does not need them yet

### 3. Shared-first thinking
Because this is Kotlin Multiplatform:
- prefer shared logic in `commonMain`
- avoid Android-only assumptions in shared code
- only introduce platform-specific code when truly necessary

### 4. Separate view mode from edit mode
Whenever a screen becomes overloaded:
- create a dedicated edit screen
- do not mix viewing, editing, and action-heavy flows in the same screen unless it is genuinely simpler

### 5. Reuse before duplicating
If a reusable component already exists:
- extend it
- adapt it carefully
- do not create a second version of the same concept unless required

---

## Project Structure Reference

Typical structure for GymSpot Lite:

```text
commonMain/
├── components/      # Reusable UI components
├── data/            # Fake data now, later remote sources/repositories
├── model/           # Domain/data models
├── navigation/      # Routes, nav config, wrapper
├── screens/         # App screens
└── state/           # Shared app/routine/workout state
````

Future evolution may introduce:

* `repository/`
* `remote/`
* `usecase/`
* `viewmodel/`

But only when needed.

---

## Pattern 1: Domain Model

Models live in `model/`.

Use models to represent app concepts clearly and minimally.

```kotlin
package com.example.multiplatform.model

data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: MuscleGroup,
    val instructions: String
)
```

### Rules

* Use `data class` for plain models
* Keep models framework-free
* Prefer strong typing (`MuscleGroup` enum / sealed type) over raw strings
* Avoid UI-specific logic inside models
* Keep the model focused on the concept it represents

### Typical models in GymSpot Lite

* `Exercise`
* `MuscleGroup`
* `RoutineExercise`
* later: `WorkoutSession`, `WorkoutSet`, `UserProfile`

---

## Pattern 2: Enum / Typed Category Model

Use enums or sealed structures for controlled categories such as muscle groups.

```kotlin
package com.example.multiplatform.model

enum class MuscleGroup(
    val displayName: String
) {
    CHEST("Chest"),
    LEGS("Legs"),
    GLUTES("Glutes"),
    CORE("Core"),
    BACK("Back"),
    ARMS("Arms"),
    SHOULDERS("Shoulders")
}
```

### Rules

* Prefer typed categories over raw strings
* Keep display values close to the enum when useful
* Add icon references only if that matches the project style
* Use these values consistently for filtering and UI

---

## Pattern 3: Fake Data Source (MVP Stage)

Temporary exercise data lives in `data/`.

```kotlin
package com.example.multiplatform.data

val fakeExercises = listOf(
    Exercise(
        id = "1",
        name = "Push Up",
        muscleGroup = MuscleGroup.CHEST,
        instructions = "Keep your body straight and lower yourself until your chest nearly touches the floor."
    )
)
```

### Rules

* Use fake data only as a temporary development source
* Keep it realistic enough to support UI flows
* Group data consistently by the same category system used in the UI
* Do not mix fake and real API data in the same flow without explicit intent

### Transition rule

Once API integration starts:

* keep fake data only as fallback/dev seed data
* do not continue expanding fake data unnecessarily

---

## Pattern 4: Shared State Object (MVP Pattern)

For MVP-sized features, shared state can live in `state/` as a singleton object.

```kotlin
package com.example.multiplatform.state

object RoutineState {
    var name: String = "My Routine"
        private set

    private val _routine = mutableListOf<Exercise>()
    val routine: List<Exercise>
        get() = _routine

    fun renameRoutine(newName: String) {
        name = newName
    }

    fun addExercise(exercise: Exercise) {
        if (_routine.none { it.id == exercise.id }) {
            _routine.add(exercise)
        }
    }
}
```

### Rules

* Use shared state objects only while the project remains small enough
* Keep public API explicit and readable
* Expose read-only views when possible
* Do not expose internal mutable collections directly
* State mutations should happen through named methods

### Good use cases for this pattern

* `RoutineState`
* temporary `WorkoutSessionState`

### When to move beyond this

When state becomes:

* too coupled to many screens
* hard to reason about
* too reactive or lifecycle-dependent

then evolve to:

* `StateFlow`
* ViewModel
* more structured state containers

---

## Pattern 5: Reusable Compose Component

Reusable UI pieces live in `components/`.

```kotlin
package com.example.multiplatform.components

@Composable
fun CategoryCard(
    category: MuscleGroup,
    onClick: () -> Unit
) {
    // reusable card UI
}
```

### Rules

* Extract repeated UI into reusable components
* Keep component API small and clear
* Prefer semantic names (`TopBar`, `CategoryCard`, `ExerciseItem`)
* Avoid putting screen-level navigation logic inside components
* Components should receive callbacks, not know app routing

### Typical reusable components in GymSpot Lite

* `TopBar`
* `CategoryCard`
* `ExerciseItem`
* later: `WorkoutProgressBar`, `RoutineEditorItem`

---

## Pattern 6: Screen Composable

Screens live in `screens/`.

Each screen should have a clear purpose and limited responsibility.

```kotlin
package com.example.multiplatform.screens

@Composable
fun ExercisesScreen(
    onCategoryClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    // screen orchestration + UI
}
```

### Rules

* Screens orchestrate UI and user flow
* Screens should not contain unrelated app-wide logic
* Prefer explicit callbacks over hidden dependencies
* Use screens for one primary purpose
* If a screen is doing too much, split it

### Screen examples in GymSpot Lite

* `HomeScreen`
* `ExercisesScreen` (Categories)
* `CategoryExercisesScreen`
* `ExerciseDetailScreen`
* `MyRoutineScreen`
* `EditRoutineScreen`
* `WorkoutScreen`

---

## Pattern 7: Top Bar / Navigation Header

Top navigation headers should be reusable and consistent.

```kotlin
package com.example.multiplatform.components

@Composable
fun TopBar(
    title: String,
    showRoutineIcon: Boolean = false,
    onRoutineIconClick: () -> Unit = {},
    showBackIcon: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    // shared top bar
}
```

### Rules

* Reuse one main `TopBar` component
* Do not duplicate top bars inside each screen unless required
* Support optional:

  * back action
  * routine access
* Keep icon usage intuitive
* Avoid cluttering the header with too many actions

### Product rule

The top bar should gradually make the app feel like a real product:

* stable
* consistent
* predictable

---

## Pattern 8: Route Definition

Routes live in `navigation/Route.kt`.

```kotlin
sealed class Route : NavKey {

    @Serializable
    data object Home : Route()

    @Serializable
    data object Exercises : Route()

    @Serializable
    data class CategoryExercises(val categoryName: String) : Route()

    @Serializable
    data class ExerciseDetail(val exerciseId: String) : Route()

    @Serializable
    data object MyRoutine : Route()
}
```

### Rules

* One route per navigable destination
* Use typed route arguments when needed
* Prefer explicit route models over string concatenation
* Keep route naming aligned with screen meaning
* If view mode and edit mode are different, create separate routes

### Good examples

* `Route.MyRoutine`
* `Route.EditRoutine`
* `Route.Workout`

---

## Pattern 9: Navigation Configuration

Navigation config lives in `navigation/NavConfig.kt`.

```kotlin
val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Home::class, Route.Home.serializer())
            subclass(Route.Exercises::class, Route.Exercises.serializer())
            subclass(Route.CategoryExercises::class, Route.CategoryExercises.serializer())
        }
    }
}
```

### Rules

* Every serializable route subtype must be registered
* Keep `NavConfig` updated whenever a new route is introduced
* Missing route registration is a high-priority bug
* Route config should reflect the real app flow, not old screen names

---

## Pattern 10: Navigation Wrapper

Navigation orchestration lives in `navigation/NavigationWrapper.kt`.

```kotlin
@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack(navConfig, Route.Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onStartClick = { backStack.add(Route.Exercises) },
                    onNavigateToRoutine = { backStack.add(Route.MyRoutine) }
                )
            }
        }
    )
}
```

### Rules

* There must be only one entry per route
* Do not duplicate route entries
* Navigation wrapper should express user flow clearly
* Pass callbacks explicitly into screens
* Use it as the central truth for screen flow

### High-priority checks

* no duplicated `entry<Route.X>`
* no missing route wiring
* no dead callbacks
* no screen requiring a callback that wrapper does not provide

---

## Pattern 11: Category-Based Explore Flow

Explore should be structured, not flat.

### Recommended flow

* `Home`
* `Explore`
* `Categories`
* `CategoryExercises`
* `ExerciseDetail`

### Rules

* Categories are the main entry into exploration
* Category cards should be visually strong and clean
* Category detail should only list relevant exercises
* Add-to-routine should happen in `ExerciseDetailScreen`, not in categories list
* Keep exploration and action separated

---

## Pattern 12: View Mode vs Edit Mode

When dealing with routines, separate normal viewing from editing.

### Example

* `MyRoutineScreen` → view mode
* `EditRoutineScreen` → edit mode

### Rules

* Do not overload `MyRoutineScreen` with too many editing actions
* Put rename/reorder/remove inside a dedicated edit screen
* Use view mode to:

  * inspect routine
  * start workout
  * open edit mode

This keeps the UI cleaner and easier to understand.

---

## Pattern 13: Routine Editing Behavior

Routine editing should happen through explicit actions, not hidden side effects.

### Supported editing actions

* rename routine
* remove exercise
* reorder exercises
* later: edit sets/reps/time

### MVP-first rule

Start simple:

* up/down reorder buttons first
* drag & drop only later if needed

### Why

Functionality comes before polish.

---

## Pattern 14: Workout Session Logic

Workout logic should be treated as a dedicated feature, not just a screen.

### Minimum expected behavior

* current exercise
* current set
* total sets
* reps and/or duration
* next step
* progress
* completion state

### Future state

This may later evolve into:

* `WorkoutSessionState`
* or a dedicated ViewModel / state holder

### Rules

* Keep workout flow isolated
* Do not bury workout logic inside unrelated screens
* Make workout progression explicit and predictable

---

## Pattern 15: API Integration (Future-Ready Pattern)

When replacing fake data with a real API, use a lightweight but structured approach.

### Suggested future structure

```text
data/
├── remote/
│   ├── api/
│   ├── dto/
│   ├── mapper/
│   └── datasource/
├── repository/
```

### Rules

* API responses should map into internal models
* Keep API DTOs separate from app/domain models
* Do not use DTOs directly in UI
* Introduce repositories only when the real API is added
* Do not build repository/use-case layers before they are needed

### Important

GymSpot Lite should evolve toward this pattern naturally, not all at once.

---

## Pattern 16: ViewModel / StateFlow (Future Evolution Pattern)

At the current stage, simple state objects are acceptable.

When the app grows, move to:

* `ViewModel`
* `MutableStateFlow`
* `StateFlow`

### Use when:

* screen state becomes complex
* loading/error/success states are needed
* API-backed data enters the flow
* routine/workout interactions become lifecycle-sensitive

### Rules

* keep `MutableStateFlow` private
* expose `StateFlow` publicly
* use screen state models when needed
* migrate incrementally, not all at once

---

## Pattern 17: UI Feedback

User feedback should be immediate and obvious.

### Examples

* add exercise → visual confirmation
* remove exercise → reflected immediately
* completed workout → visible completed state
* invalid or missing content → visible fallback

### Rules

* avoid weak or ambiguous feedback
* prefer clear icons or short status messages
* avoid leaving users wondering if an action worked

---

## Pattern 18: Naming / Copywriting

Labels matter a lot in this project because part of the goal is to make the app feel real.

### Good direction

* `Explore`
* `Categories`
* `My Routine`
* `Edit Routine`
* `Workout`

### Avoid

* overly technical or demo-sounding labels
* debug text
* raw placeholders
* inconsistent naming between route and UI

---

## Full Feature Checklist

When adding a new feature to GymSpot Lite, use this checklist.

### Navigation / Screen Feature

* [ ] Route added in `Route.kt`
* [ ] Route registered in `NavConfig.kt`
* [ ] Entry added in `NavigationWrapper.kt`
* [ ] Screen created in `screens/`
* [ ] Required callbacks passed correctly
* [ ] User flow tested end-to-end

### Reusable UI Feature

* [ ] Component created in `components/`
* [ ] Props are minimal and clear
* [ ] No screen-specific navigation logic inside component
* [ ] Reused where appropriate

### Routine Feature

* [ ] State method added to `RoutineState`
* [ ] Screen flow updated
* [ ] View mode vs edit mode evaluated
* [ ] Feedback visible in UI

### Workout Feature

* [ ] Session flow defined
* [ ] Progress represented
* [ ] Completion state handled
* [ ] Next-step behavior tested

### API Feature (future)

* [ ] DTO created
* [ ] Mapper created
* [ ] Data source created
* [ ] Repository added
* [ ] Screen state updated
* [ ] UI no longer depends on fake data

---

## Recommended Feature Order

Unless the user explicitly changes priorities, GymSpot Lite should evolve in this order:

1. Explore flow
2. Persistent access to routines
3. Edit routine
4. Workout logic
5. Real exercise API
6. Login / Profile
7. UI polish / advanced navigation

---

## Final Rule

GymSpot Lite should evolve like a real product:

* one block at a time
* one stable checkpoint at a time
* no unnecessary chaos
* no premature architecture
* no fake complexity

Build what the app needs now, while keeping the path open for what it will need next.

```
```
