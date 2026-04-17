# GymSpot Lite - Copilot Instructions

## Project Overview

GymSpot Lite is a Kotlin Multiplatform fitness application built with Compose Multiplatform.

The app is focused on:
- exploring exercises
- browsing exercises by category
- viewing exercise details
- building workout routines
- editing routines
- running workout sessions

It is a lightweight MVP inspired by the larger GymSpot idea, but designed to be realistic, expandable, and structured like a real product rather than a technical demo.

The long-term goal is for GymSpot Lite to evolve into a more complete multiplatform fitness app with:
- real exercise API integration
- better workout logic
- richer routine management
- login/profile
- stronger UI/UX

---

## Architecture

Current architecture is lightweight and practical, centered around Kotlin Multiplatform + Compose Multiplatform.

### Current structure
- **Components**: reusable UI elements such as top bars, cards, and routine items
- **Screens**: user-facing screens such as Home, Categories, Exercise Detail, My Routine, Workout
- **Navigation**: route definitions, navigation configuration, and navigation wrapper
- **State**: shared state objects for routine and workout logic (MVP approach)
- **Data**: fake/local exercise data for development and UI flow validation
- **Model**: core domain-like models such as `Exercise` and `MuscleGroup`

### Current architectural style
- Kotlin Multiplatform shared code in `commonMain`
- Compose Multiplatform for UI
- Route-based navigation
- Shared state objects for MVP-scale features
- Reusable components to avoid UI duplication

### Architecture direction
The project is intentionally not overengineered yet.

As the app grows, it may evolve toward:
- repositories
- remote data sources
- DTO + mapper structure
- ViewModel + StateFlow
- more structured workout/session state

But only when the project actually needs them.

---

## Tech Stack

- **Language**: Kotlin
- **Platform model**: Kotlin Multiplatform
- **UI**: Compose Multiplatform + Material 3
- **Navigation**: Navigation 3 style route-based flow
- **State**: shared state objects (MVP stage)
- **Data source**: fake local exercise data for now
- **Icons/UI assets**: Material icons / Compose UI components
- **Build**: Gradle with Kotlin Multiplatform configuration

### Planned / future stack additions
- Real exercise API
- Remote data layer
- DTOs + mapping
- ViewModel / StateFlow where complexity requires it
- Possibly auth/profile handling

---

## Key Patterns

- Navigation is route-based and centralized in `NavigationWrapper`
- Every new screen should have a route in `Route.kt`
- Every route must be registered in `NavConfig.kt`
- Shared UI elements should be extracted into reusable components
- Exploration should be category-based, not flat
- Add-to-routine happens in `ExerciseDetailScreen`, not in category browsing
- View mode and edit mode should be separated when routine complexity grows
- Shared state is currently acceptable for MVP-scale flows
- User flow should be explicit and easy to trace through callbacks

---

## Code Conventions

- Code and comments in English
- High-level documentation may be in Spanish if needed for project docs
- Prefer descriptive names
- Prefer typed models over raw strings
- Prefer reusable components over duplicated screen UI
- Keep navigation explicit
- Keep shared logic multiplatform-friendly
- Avoid Android-only imports in shared code
- Avoid unnecessary dependencies
- Do not introduce heavy architecture before needed

### Naming style
Use clear, product-aligned names:
- `HomeScreen`
- `ExercisesScreen`
- `CategoryExercisesScreen`
- `ExerciseDetailScreen`
- `MyRoutineScreen`
- `EditRoutineScreen`
- `WorkoutScreen`

For reusable UI:
- `TopBar`
- `CategoryCard`
- `ExerciseItem`

For state:
- `RoutineState`
- later: `WorkoutSessionState`

For routes:
- `Route.Home`
- `Route.Exercises`
- `Route.CategoryExercises`
- `Route.ExerciseDetail`
- `Route.MyRoutine`
- `Route.EditRoutine`
- `Route.Workout`

---

## Product / UX Principles

GymSpot Lite should increasingly feel like a real app.

### This means:
- the user should not need to go back to Home constantly
- exploration should be visually structured
- routine access should be intuitive
- edit flows should be separate from view flows
- screens should have one clear main purpose
- navigation should feel stable and predictable

### Avoid:
- demo-like text
- raw placeholders
- too many buttons on the same screen
- duplicated headers
- noisy category cards
- overloading screens with both view and edit concerns

---

## Current Project Structure

```text
commonMain/
├── components/      # Reusable UI components
├── data/            # Fake data now, later remote sources/repositories
├── model/           # Core models
├── navigation/      # Routes, nav config, navigation wrapper
├── screens/         # App screens
└── state/           # Shared routine/workout state
````

### Typical files

* `components/TopBar.kt`
* `components/CategoryCard.kt`
* `components/ExerciseItem.kt`
* `navigation/Route.kt`
* `navigation/NavConfig.kt`
* `navigation/NavigationWrapper.kt`
* `screens/HomeScreen.kt`
* `screens/ExercisesScreen.kt`
* `screens/CategoryExercisesScreen.kt`
* `screens/ExerciseDetailScreen.kt`
* `screens/MyRoutineScreen.kt`
* `screens/EditRoutineScreen.kt`
* `screens/WorkoutScreen.kt`
* `state/RoutineState.kt`
* `data/FakeExercises.kt`

---

## Current Feature Roadmap

Unless the user explicitly changes priorities, GymSpot Lite should generally evolve in this order:

1. Explore flow
2. Persistent access to routines
3. Edit routine
4. Workout logic
5. Real exercise API
6. Login / Profile
7. UI polish / advanced navigation

---

## Current Screen Flow

### Explore flow

* `Home`
* `Explore`
* `Categories`
* `CategoryExercises`
* `ExerciseDetail`

### Routine flow

* `MyRoutine`
* later: `EditRoutine`

### Workout flow

* `Workout`

This flow should remain explicit in the navigation wrapper.

---

## Build / Run Expectations

The project should be kept stable and testable across the development environments being used.

### Important working principles

* keep the same branch state across both computers
* checkpoint completed blocks before starting a new one
* avoid diverging local configurations unnecessarily
* prefer branch-per-feature workflow

### Practical workflow

* finish a block
* test it
* commit it
* push it
* pull it on the other machine
* continue from the same state

---

## Documentation Reference

Recommended internal references for GymSpot Lite should include:

* project overview / README
* implementation patterns
* architecture notes
* roadmap / block planning
* future API integration notes

If more formal docs are added later, they should reflect the actual project stage instead of pretending the app is already fully production-ready.

---

## Working Rules for Copilot / Agent Behavior

When assisting in this project:

### Always do this

* read related existing files first
* respect the current feature block
* implement in small vertical slices
* reuse existing components when possible
* keep navigation explicit
* prefer minimal correct changes over broad refactors
* separate what is already done from what still needs to be done
* keep the app coherent with its current stage

### Never do this

* invent architecture that does not exist yet
* pretend APIs or login already exist
* introduce unnecessary dependencies
* duplicate reusable UI patterns without reason
* mix several major features at once
* overengineer MVP code
* ignore multiplatform constraints

---

## Recommended Development Style

### When adding a new feature

1. Identify the feature block
2. Identify affected files
3. Implement the smallest correct navigation/state/UI slice
4. Verify the flow end-to-end
5. Commit as a stable checkpoint
6. Only then move to the next block

### When adding a new screen

* add route
* register route
* add wrapper entry
* create screen
* pass callbacks explicitly
* test navigation

### When improving UI

* prefer reusable components
* remove visual noise
* make the app feel more product-like
* do not break user flow for cosmetic changes

### When extending state

* add explicit methods to state objects
* keep mutation controlled
* avoid exposing raw mutable internals
* move to richer state models only when complexity truly requires it

---

## Long-Term Direction

GymSpot Lite should evolve into a coherent multiplatform fitness app that feels:

* structured
* intuitive
* maintainable
* visually improving
* realistic for a student project
* scalable without becoming chaotic

The goal is not to imitate enterprise complexity.

The goal is to build the right amount of structure for the project stage, while keeping a clear path toward:

* API-backed exercises
* richer routine editing
* workout session tracking
* login/profile
* polished navigation and UI

```
```
