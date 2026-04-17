---
name: implementation-patterns
description: "GymSpot Lite implementation patterns and templates for Kotlin Multiplatform / Compose Multiplatform. Use when: creating new features, adding new screens, implementing navigation flows, building reusable components, extending routine/workout logic, introducing real API-backed exercise data, or following project conventions. Provides step-by-step procedures and templates aligned with GymSpot Lite's current architecture and growth path."
---

# Implementation Patterns for GymSpot Lite

Templates and procedures for implementing features following GymSpot Lite's current Kotlin Multiplatform + Compose architecture.

GymSpot Lite is an MVP-first fitness app focused on:
- explore flow
- exercise categories
- exercise detail
- routines
- workout mode

This guide exists to help implement features consistently without introducing unnecessary complexity.

---

## When to Use

Use this reference when:

- Adding a new feature end-to-end
- Creating a new screen
- Adding a new route or navigation flow
- Building a reusable UI component
- Extending routine logic
- Implementing workout logic
- Replacing fake data with real API-backed data
- Cleaning up or expanding the current project structure
- Needing a step-by-step path that matches GymSpot Lite's conventions

---

## Project Mindset

GymSpot Lite is **not** a heavy enterprise app at this stage.

Patterns should support:
- readability
- incremental development
- good multiplatform habits
- future scalability

Patterns should avoid:
- premature abstractions
- unnecessary dependencies
- overly complex architecture before it is needed

---

## Procedure

### 1. Identify the Feature Scope

Before writing code, determine **what kind of feature this is**.

Ask:

- **Is this a navigation change?**
  - Add/update `Route`
  - Update `NavConfig`
  - Update `NavigationWrapper`
  - Update affected screens

- **Is this a new screen?**
  - Create screen composable
  - Add route
  - Wire callbacks in navigation

- **Is this a reusable UI pattern?**
  - Create or extend a component in `components/`

- **Is this routine-related logic?**
  - Extend `RoutineState`
  - Update routine screens
  - Keep view mode vs edit mode separate if needed

- **Is this workout logic?**
  - Add session-related state
  - Update `WorkoutScreen`
  - Keep workout progression explicit

- **Is this real API data?**
  - Introduce remote layer progressively
  - Add DTOs, mappers, repository, and screen state when the project actually needs them

---

### 2. Implement in the Smallest Correct Slice

Prefer **small vertical slices** over broad refactors.

Examples:

- New category screen:
  - route
  - screen
  - wrapper entry
  - test flow

- Routine edit feature:
  - route
  - screen
  - `RoutineState` methods
  - navigation from `MyRoutineScreen`

- Top bar improvement:
  - update `TopBar`
  - update screens using it
  - update navigation callbacks if needed

Do **not** redesign unrelated areas unless the user explicitly asks for it.

---

### 3. Follow the Project's Current Evolution Stage

GymSpot Lite currently uses:

- Kotlin Multiplatform
- Compose Multiplatform
- route-based navigation
- lightweight shared state objects
- fake local data

That means:

### Use now
- shared state objects for small features
- reusable Compose components
- explicit callbacks
- simple navigation structures
- fake data for MVP flows

### Introduce later, only when needed
- repositories
- API remote data sources
- DTOs and mappers
- ViewModels
- StateFlow
- login/session management
- persistence

Do not jump to the future architecture too early.

---

### 4. Build Bottom-Up When a Feature Requires Data + UI

For features that touch both state and screens, implement in this order:

1. Model or typed structure
2. State changes
3. Route (if needed)
4. Navigation wiring
5. Screen
6. Reusable component extraction
7. User flow verification

This is the preferred order for GymSpot Lite right now.

---

### 5. Wire Navigation Explicitly

Navigation is one of the most important parts of this project.

Whenever you add a screen:

1. Add a new route to `Route.kt`
2. Register it in `NavConfig.kt`
3. Add the `entry<Route.X>` in `NavigationWrapper.kt`
4. Pass explicit callbacks to the screen
5. Test back flow and cross-navigation

---

### 6. Verify Before Expanding the Scope

Before moving to the next block, verify:

- the screen renders correctly
- navigation works
- back works
- cross-navigation works
- state updates are visible
- no duplicate route entries exist
- no dead callbacks remain

Only then open the next feature block.

---

## Current Project Structure Reference

```text
commonMain/
├── components/      # Reusable UI components
├── data/            # Fake data now, later remote sources/repositories
├── model/           # Core models (Exercise, MuscleGroup, etc.)
├── navigation/      # Routes, nav config, wrapper
├── screens/         # App screens
└── state/           # Shared routine/workout state
````

---

## Current Preferred Feature Order

Unless the user changes the roadmap, GymSpot Lite should generally evolve in this order:

1. Explore flow
2. Persistent access to routines
3. Edit routine
4. Workout logic
5. Real exercise API
6. Login / Profile
7. UI polish / advanced navigation

---

## Implementation Patterns

---

## Pattern 1: Route-Based Screen Flow

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

    @Serializable
    data object EditRoutine : Route()

    @Serializable
    data object Workout : Route()
}
```

### Use when

* Adding a new screen
* Splitting edit mode from view mode
* Adding a new user flow step

### Rules

* One route per navigable destination
* Use typed arguments instead of raw route strings
* Register every route in `NavConfig`
* Keep names aligned with the product meaning

---

## Pattern 2: Navigation Wrapper Entry

Navigation orchestration lives in `NavigationWrapper.kt`.

```kotlin
entry<Route.Exercises> {
    ExercisesScreen(
        onCategoryClick = { categoryName ->
            backStack.add(Route.CategoryExercises(categoryName))
        },
        onBack = {
            backStack.removeLastOrNull()
        },
        onNavigateToRoutine = {
            backStack.add(Route.MyRoutine)
        }
    )
}
```

### Use when

* Wiring any screen into the app
* Passing navigation callbacks
* Adding new route transitions

### Rules

* One `entry<Route.X>` per route
* No duplicated entries
* All required callbacks passed explicitly
* Wrapper is the source of truth for user flow

---

## Pattern 3: Screen Composable

Screens live in `screens/`.

```kotlin
@Composable
fun CategoryExercisesScreen(
    categoryName: String,
    onExerciseClick: (String) -> Unit,
    onBack: () -> Unit,
    onNavigateToRoutine: () -> Unit
) {
    // UI + orchestration
}
```

### Use when

* Building a user-facing screen
* Creating a new app flow step
* Updating a screen to support navigation or state changes

### Rules

* Screens orchestrate UI and callbacks
* Screens should not hide navigation dependencies
* Prefer clear screen APIs over hidden global behavior
* If a screen becomes overloaded, split responsibilities

---

## Pattern 4: Shared State Object

Small-to-medium app state currently lives in `state/`.

```kotlin
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

    fun removeExercise(exercise: Exercise) {
        _routine.removeAll { it.id == exercise.id }
    }
}
```

### Use when

* The project is still MVP-scale
* A simple shared state model is enough
* Several screens need lightweight access to the same information

### Rules

* Keep internal mutable state private
* Expose read-only access where possible
* Mutate through named methods
* Prefer clarity over cleverness

### Evolve later to

* `StateFlow`
* ViewModel
* screen state holders

only when complexity really requires it.

---

## Pattern 5: Reusable UI Component

Reusable UI components live in `components/`.

```kotlin
@Composable
fun CategoryCard(
    category: MuscleGroup,
    onClick: () -> Unit
) {
    // reusable card UI
}
```

### Use when

* UI repeats in more than one screen
* A screen becomes visually noisy
* A navigation header, card, or item pattern should be shared

### Rules

* Keep component props small and meaningful
* Pass callbacks instead of route objects
* Keep components reusable and screen-agnostic
* Extend existing components before creating parallel versions

---

## Pattern 6: Shared Top Bar

Use a single reusable top bar component whenever possible.

```kotlin
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

### Use when

* A screen needs a title
* A screen needs back navigation
* A screen needs persistent access to routines

### Rules

* Prefer reusing the existing `TopBar`
* Do not recreate top bars inside each screen
* Keep icon behavior explicit
* Use the top bar to support app-like navigation consistency

---

## Pattern 7: Category-Based Explore Flow

Explore should be structured, not flat.

### Recommended structure

* `Home`
* `Explore`
* `Categories`
* `CategoryExercises`
* `ExerciseDetail`

### Screen responsibilities

* `HomeScreen` → start points
* `ExercisesScreen` → categories
* `CategoryExercisesScreen` → exercises in one category
* `ExerciseDetailScreen` → action point for adding to routine

### Rules

* Categories are the main exploration entry
* Category list should feel visual and clean
* Add-to-routine should happen in detail, not categories
* Keep browsing and acting separate

---

## Pattern 8: Exercise Detail as Action Screen

`ExerciseDetailScreen` is the correct place for:

* viewing full exercise info
* adding the exercise to routine
* later: removing / toggling state if needed

```kotlin
@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onAddToRoutine: (Exercise) -> Unit,
    onNavigateToRoutine: () -> Unit,
    onBack: () -> Unit
) {
    // detail + action
}
```

### Rules

* Keep add/remove logic here, not in category browsing
* Use clear UI feedback
* Route to routine from the top bar if the block requires it
* Handle invalid exercise IDs safely

---

## Pattern 9: View Mode vs Edit Mode

Routine view and routine editing should be separate.

### Recommended structure

* `MyRoutineScreen` → view mode
* `EditRoutineScreen` → edit mode

### Use when

* Editing starts growing beyond a simple name field
* You need remove/reorder interactions
* The main routine screen should stay clean

### Rules

* Keep `MyRoutineScreen` focused on viewing and starting actions
* Put rename/reorder/remove in `EditRoutineScreen`
* Do not overload view mode with too many controls

---

## Pattern 10: Routine Editing

When implementing routine editing, follow this order:

1. Rename routine
2. Remove exercises
3. Reorder exercises
4. Open exercise detail from routine
5. Later: sets / reps / time

### MVP-first reorder pattern

Start with:

* move up
* move down

instead of drag & drop.

### Why

* easier to implement
* easier to debug
* still functionally solves the problem

---

## Pattern 11: Workout Logic

Workout mode should be treated as a dedicated feature block.

### Minimum structure

* show current exercise
* show set number
* show reps or duration
* move to next step
* show progress
* show completion state

### Suggested future state holder

```kotlin
object WorkoutSessionState {
    // later, when needed
}
```

### Rules

* Keep workout progression explicit
* Avoid burying workout logic across unrelated screens
* Add complexity only when the basic loop works

---

## Pattern 12: Fake Data to Real API Migration

When the project is ready to move beyond fake exercise data:

### Introduce gradually

```text
data/
├── remote/
│   ├── api/
│   ├── dto/
│   ├── mapper/
│   └── datasource/
├── repository/
```

### Migration order

1. DTOs
2. API interface
3. remote data source
4. mapper
5. repository
6. screen integration
7. remove fake-data dependency from main flow

### Rules

* Do not use DTOs directly in screens
* Map remote data to internal models
* Do not create repository/use-case layers too early
* Introduce them when the real API is actually added

---

## Pattern 13: Future ViewModel / StateFlow Adoption

At the current stage, shared state objects are acceptable.

When introducing real API-backed flows or more complex screens, evolve to:

```kotlin
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow<UiState<MyData>>(UiState.Idle)
    val state: StateFlow<UiState<MyData>> = _state.asStateFlow()
}
```

### Use when

* screen states need loading/error/success
* API-backed content arrives
* screen logic becomes harder to manage directly

### Rules

* Keep `MutableStateFlow` private
* Expose `StateFlow`
* Migrate incrementally
* Do not force ViewModels into every simple screen before needed

---

## Pattern 14: Naming / Copywriting

The app should sound like a product, not a demo.

### Preferred labels

* `Explore`
* `Categories`
* `My Routine`
* `Edit Routine`
* `Workout`

### Avoid

* debug placeholders
* overly technical names
* raw temporary labels
* inconsistent wording between screens

---

## Pattern 15: UI Feedback

User actions should always feel acknowledged.

### Examples

* add exercise → visible confirmation
* remove exercise → immediate visual update
* completed routine → visible completed state
* invalid content → safe fallback

### Rules

* Use clear, lightweight feedback
* Avoid weak or confusing UI signals
* Do not leave actions feeling invisible

---

## Step-by-Step Procedures

---

## Procedure A: Add a New Screen

Use this when adding any new screen.

### Steps

1. Define the route in `Route.kt`
2. Register it in `NavConfig.kt`
3. Create the composable in `screens/`
4. Add the wrapper entry in `NavigationWrapper.kt`
5. Pass callbacks explicitly
6. Test the full navigation path

### Checklist

* [ ] Route added
* [ ] Route registered
* [ ] Screen created
* [ ] Wrapper entry added
* [ ] Navigation tested

---

## Procedure B: Add a New Reusable Component

Use this when UI is repeated or cluttered.

### Steps

1. Identify repeated UI pattern
2. Create component in `components/`
3. Keep props minimal
4. Replace duplicated UI with component
5. Verify behavior in all screens using it

### Checklist

* [ ] Component extracted
* [ ] Props are clear
* [ ] No route logic inside component
* [ ] Reused correctly

---

## Procedure C: Add a New Routine Feature

Use this when extending routines.

### Steps

1. Add method(s) to `RoutineState`
2. Update screen flow
3. Decide whether feature belongs in view mode or edit mode
4. Update relevant screen(s)
5. Test state changes visually

### Checklist

* [ ] State updated
* [ ] UI updated
* [ ] View/edit separation respected
* [ ] User feedback visible

---

## Procedure D: Add a New Explore Flow Step

Use this when changing exploration/navigation.

### Steps

1. Define screen purpose
2. Add route if needed
3. Wire route in wrapper
4. Ensure category / exercise model supports it
5. Update labels / top bar
6. Test full browse path

### Checklist

* [ ] Route added if needed
* [ ] Wrapper updated
* [ ] Screen connected
* [ ] Explore path tested
* [ ] Labels aligned with product language

---

## Procedure E: Introduce Real API Data

Use this when replacing fake data.

### Steps

1. Define DTOs
2. Define API service
3. Create remote data source
4. Create mappers
5. Create repository
6. Update screen/state integration
7. Phase out fake data from main flow

### Checklist

* [ ] DTO added
* [ ] API layer added
* [ ] Mapper added
* [ ] Repository added
* [ ] UI updated
* [ ] Fake data dependency reduced

---

## Full Feature Checklist

When adding any GymSpot Lite feature, use this checklist.

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

### API Feature

* [ ] DTO created
* [ ] Mapper created
* [ ] Data source created
* [ ] Repository added
* [ ] Screen state updated
* [ ] UI no longer depends on fake data

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
