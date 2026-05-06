# CLAUDE.md

Agent instructions for Claude Code. For full project context, architecture, and features read **README.md** first.

## Build Commands

```bash
./gradlew :composeApp:run                          # Desktop (JVM)
./gradlew :composeApp:assembleDebug                # Android APK
./gradlew :composeApp:jvmTest                      # JVM tests
./gradlew :composeApp:wasmJsBrowserDevelopmentRun  # Web (WASM)
```

CI runs `assembleDebug` + `jvmTest` on push/PR to `main`. JDK 17 required.

## Git Workflow

- **Never work directly on `main`** — always create a feature branch
- Branch naming: `feature/<name>`, `fix/<name>`, `refactor/<scope>`
- Commit prefixes: `feat:`, `fix:`, `refactor:`, `docs:`, `chore:`
- Before a PR: build must pass, desktop test required, no architecture regression

## Agent Behavior Rules

**Before editing files, refactoring architecture, deleting classes, renaming packages, changing schemas, or taking git actions:**
1. Analyze
2. Explain the proposed change
3. Propose the approach
4. Wait for user approval

Never make silent changes. Never take these actions without explicit user approval.

## Architecture

Layer structure and data flows are documented in `README.md`. Key rules:

- All code and inline comments in **English**
- No Android-only imports in `commonMain`
- Keep mutations controlled: expose read-only state, mutate through named methods only
- `add-to-routine` only in `ExerciseDetailScreen`
- View and edit mode are separate screens (`MyRoutineScreen` vs `EditRoutineScreen`)

## Adding a New Screen

1. Add route to `navigation/Route.kt`
2. Register it in `navigation/NavConfig.kt`
3. Create the composable in `screens/`
4. Add `entry<Route.X>` in `NavigationWrapper.kt` with explicit callbacks
5. Test navigation, back, and cross-navigation

## Coding Standards

- Preserve existing package structure and KMP compatibility
- Prefer extension over replacement
- No random refactors, no silent deletions, no schema breaks without approval
- Do not overengineer beyond the current phase (see `PLAN.md`)
- No docstrings, comments, or type annotations added to unchanged code

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

Detailed patterns: `.github/skills/implementation-patterns/SKILL.md`
