# GymSpot Lite — Kotlin Multiplatform

A fitness app built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. Users browse real exercises from the Wger API, create and manage multiple workout routines saved to the cloud, track favorites, and execute workouts with sets, reps, and rest timers. Targets Android, Desktop (JVM), Web (WASM), and iOS from a single shared codebase.

---

## Features

- **Browse Exercises** — Real data from the Wger API, filtered by language (EN/ES), organized by muscle group
- **Multiple Routines** — Create, name, and manage multiple workout routines; each saved to the cloud per user
- **Workout Mode** — Execute workouts with sets/reps tracking, 60s rest timer, and completion summary
- **Favorites** — Save exercises and access them from your profile
- **Authentication** — Supabase email/password login; every user's data is fully isolated
- **Quick Start Templates** — Predefined routines: Push Day, Pull Day, Leg Day, Full Body, Upper Body, Core Blast
- **Multilingual** — English and Spanish exercise libraries

---

## Tech Stack

| Library | Version | Role |
|---|---|---|
| Kotlin Multiplatform | 2.x | Shared logic across all targets |
| Compose Multiplatform | 1.10.3 | UI across Android, Desktop, Web, iOS |
| Navigation 3 (JetBrains) | 1.0.0-alpha06 | Back-stack navigation with serializable routes |
| Ktor Client | 3.4.2 | HTTP client — Wger API |
| Supabase (Postgrest + Auth) | 3.2.2 | Cloud DB + user authentication |
| Kotlinx Serialization | 1.10.0 | Routes + JSON DTOs |
| Kotlin Coroutines | 1.10.2 | Async data loading |

---

## Architecture

```
commonMain/kotlin/
├── data/
│   ├── remote/          # Wger API (Ktor): WgerApi, WgerDataSource, WgerExerciseRepository
│   ├── supabase/        # Supabase: exercises cache, routines, favorites, auth session
│   ├── sync/            # ExerciseSyncService — Supabase-first, Wger fallback
│   └── */dto/, */mapper/
├── model/               # Exercise, Routine, RoutineTemplate, MuscleGroup, ExerciseLanguage
├── state/               # Compose singletons: AuthState, RoutineState, RoutinesListState,
│                        #   FavoritesState, AppSettingsState, WorkoutSessionState
├── navigation/          # Navigation3 routes + NavigationWrapper
└── screens/ + components/
```

### Data flows

**Exercises:** language change → `ExerciseSyncService` checks Supabase; on miss, fetches from Wger API and upserts to Supabase.

**Routines:** login → `loadAll(userId)` → `RoutinesListState`; user opens a routine → `RoutineState.loadFromRemote()`; every mutation (add/remove/rename) syncs to Supabase immediately.

### State management

MVP-grade Compose singletons (`mutableStateOf`, `mutableStateListOf`). No ViewModel layer — state objects expose read-only views and mutate through named methods only.

### Navigation

JetBrains Navigation 3 with `@Serializable` route objects. Back stack managed via `rememberNavBackStack()`. Every screen requires: a route in `Route.kt`, registration in `NavConfig.kt`, and an entry in `NavigationWrapper.kt`.

---

## Supabase Schema

| Table | Purpose |
|---|---|
| `exercises` | Language-keyed exercise cache from Wger API |
| `routines` | Named routines per user (`session_id = auth.uid()`) |
| `routine_exercises` | Exercises within each routine, ordered by position |
| `favorites` | User's saved exercises |

Row-Level Security enabled on all user tables — each user can only access their own data.

---

## Build & Run

```bash
# Desktop (JVM)
./gradlew :composeApp:run

# Android debug APK
./gradlew :composeApp:assembleDebug

# JVM tests
./gradlew :composeApp:jvmTest

# Web (WASM)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

iOS: open `iosApp/iosApp.xcodeproj` in Xcode.

> Requires JDK 17. CI runs `assembleDebug` + `jvmTest` on every push to `main`.

---

## Author

Developed by **Sara** — Kotlin Multiplatform portfolio project.
