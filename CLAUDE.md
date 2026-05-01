# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**GymSpot Lite** — a Kotlin Multiplatform (KMP) fitness app built with Compose Multiplatform. Targets Android, JVM Desktop, WASM Web, and iOS. Allows users to browse exercises (fetched from the [Wger API](https://wger.de/api/v2/)), create workout routines, and execute workouts. This is an educational/portfolio project — every implementation decision must balance learning clarity, real-world architecture, and scalability.

## Build & Run Commands

```bash
# Run desktop (JVM)
./gradlew :composeApp:run

# Build Android debug APK
./gradlew :composeApp:assembleDebug

# Run JVM tests
./gradlew :composeApp:jvmTest

# Run web (WASM)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

CI runs `assembleDebug` + `jvmTest` on push/PR to `main` (see `.github/workflows/ci-pipeline.yml`). JDK 17 is required.

## Git Workflow

- **Never work directly on `main`** — always create a feature branch
- Branch naming: `feature/<name>`, `fix/<name>`, `refactor/<scope>`
- Commit prefixes: `feat:`, `fix:`, `refactor:`, `docs:`, `chore:`
- Before a PR: build must pass, desktop test required, Android compatibility preserved, no architecture regression

## Agent Behavior Rules

**Before editing files, refactoring architecture, deleting classes, renaming packages, changing schemas, or taking git actions:**
1. Analyze
2. Explain the proposed change
3. Propose the approach
4. Wait for user approval

Never make silent changes. Never take these actions without explicit user approval.

## Architecture

### Layer Structure

```
commonMain/kotlin/
├── data/
│   ├── remote/          # Wger API (Ktor): WgerApi, WgerDataSource (JVM-only), WgerExerciseRepository
│   ├── supabase/        # Supabase cache: SupabaseClientProvider, SupabaseExerciseDataSource, SupabaseExerciseRepository
│   ├── sync/            # ExerciseSyncService — cache-first orchestrator
│   └── */dto/, */mapper/ # DTOs and domain mappers per data source
├── model/               # Domain entities: Exercise, ExerciseLanguage, MuscleGroup
├── state/               # Singleton compose state: RoutineState, AppSettingsState
├── navigation/          # Navigation3 routes and back-stack logic
└── screens/ + components/
```

`jvmMain/` contains the actual `WgerDataSource` implementation — other platforms need their own or a shared abstraction.

### Data Flow (Exercise Loading)

1. User selects language → `AppSettingsState.updateExerciseLanguage()`
2. `NavigationWrapper` `LaunchedEffect` detects change → calls `exerciseRepository.getExercises(language)`
3. `ExerciseSyncService` checks Supabase first; on cache miss, fetches from Wger API, then upserts into Supabase
4. Exercises flow down to screens via function parameters

### State Management

MVP-grade: `RoutineState` and `AppSettingsState` are Compose-observable singletons (`mutableStateOf`, `mutableStateListOf`). No ViewModel layer yet — planned for a future phase.

### Navigation

Uses **JetBrains Navigation 3** (`1.0.0-alpha06`) with `@Serializable` route objects. Back stack is managed manually via `rememberNavBackStack()`. Routes are defined as a sealed class in `navigation/Route.kt`. Every new screen requires: a route in `Route.kt`, registration in `NavConfig.kt`, and a wrapper entry in `NavigationWrapper.kt`.

### Supabase Integration

- Client is a singleton in `SupabaseClientProvider.kt` (anon/publishable key is intentionally public)
- Table: `exercises` (columns: id, name, instructions, muscle_group, language)
- Operations via PostgREST: `select()` filtered by language code, `upsert()` for bulk cache writes

### Key Domain Types

- `ExerciseLanguage` enum carries both `wgerLanguageId` (int for API filter) and `apiCode` (string for Supabase)
- `MuscleGroup` enum used for category browsing
- Wger pagination is capped at 200 exercises (`WgerDataSource`)

## Code Conventions

- All code and inline comments in **English**; project docs may be in Spanish
- Prefer typed models over raw strings (`MuscleGroup` enum, not `String`)
- No Android-only imports in `commonMain` shared code
- Keep mutations controlled: expose read-only views from state objects, mutate through named methods only
- `add-to-routine` happens in `ExerciseDetailScreen` only — not in category browsing screens
- View mode and edit mode are separate screens (`MyRoutineScreen` vs `EditRoutineScreen`)

## Adding a New Screen (checklist)

1. Add route to `navigation/Route.kt`
2. Register it in `navigation/NavConfig.kt`
3. Create the composable in `screens/`
4. Add `entry<Route.X>` in `NavigationWrapper.kt` with explicit callbacks
5. Test navigation, back, and cross-navigation

## Coding Standards

- Preserve existing package structure and KMP compatibility
- Prefer extension over replacement — do not create a second version of something that already exists
- Document major architecture changes (in commit or PR description, not inline comments)
- No random refactors, no silent deletions, no schema breaks without approval
- Do not overengineer beyond the current development phase (see `PLAN.md`)

## Key Dependencies

| Library | Version | Role |
|---|---|---|
| Compose Multiplatform | 1.10.3 | UI across all targets |
| Navigation 3 | 1.0.0-alpha06 | Back-stack navigation |
| Ktor Client | 3.4.2 | HTTP (Wger API) |
| Supabase (Postgrest) | 3.2.2 | Exercise cache DB |
| Kotlinx Serialization | 1.10.0 | Routes + JSON |
| Kotlin Coroutines | 1.10.2 | Async data loading |

## Implementation Patterns Reference

Detailed implementation patterns and step-by-step procedures are in `.github/skills/implementation-patterns/SKILL.md`. Use this when creating new features, adding screens, or following project conventions.
