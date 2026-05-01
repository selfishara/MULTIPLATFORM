# GymSpot Lite — Project Plan

## Current Assessment (April 2026)

GymSpot Lite has graduated from a simple practice app to a **MVP+ with production-style architecture**. The core data stack is solid: real API integration, cloud caching with Supabase, multilingual support, and a clean repository/sync pattern. The project is educational in origin but portfolio-grade in structure.

**What works well:**
- Clean separation of layers (remote → sync → supabase → domain → UI)
- KMP-first design: shared logic in `commonMain`, platform specifics isolated
- Functional exercise browsing with language switching (EN/ES)
- Routine creation, editing, and workout execution flow
- CI pipeline running on GitHub Actions

**Current gaps:**
- `WgerDataSource` is JVM-only — Android and other targets depend on shared stub/abstraction
- No `existsByLanguage()` guard on sync — every app launch can trigger a full re-fetch
- Routine state is in-memory only (lost on restart)
- No user authentication — all users share the same Supabase cache
- No automated tests beyond infrastructure setup
- Workout screen is functional but lacks sets/reps/timer logic

---

## Phases

### Phase 1 — Core Exercise API Integration ✅ COMPLETED

- Wger API integration (Ktor)
- DTO + mapper system
- Paginated fetch (200 exercises cap)
- Muscle group categorization
- Category browsing screens

### Phase 2 — Language Filtering System ✅ COMPLETED

- English/Spanish language model (`ExerciseLanguage` enum)
- Language preference state (`AppSettingsState`)
- Translation completeness validation
- Invalid/incomplete exercise exclusion

### Phase 3 — Persistent Supabase Cache ✅ COMPLETED

- Supabase project + PostgreSQL `exercises` table
- `SupabaseClientProvider`, DTO, mapper, repository
- `ExerciseSyncService`: Supabase → Wger → Supabase fallback pattern
- 192 exercises cached, faster subsequent loads
- Reduced dependency on external Wger API

---

### Phase 4 — Sync Optimization ✅ COMPLETED

**Branch:** `feature/sync-optimization`

- [x] `existsByLanguage(language)` — Supabase `select limit 1` guards against redundant Wger fetches
- [x] Selective/partial sync per language — each language is checked and fetched independently
- [x] Non-blocking loading — `SyncState.Loading` exposed via `StateFlow`; UI shows spinner while sync runs
- [x] Better error handling — Supabase write failures are isolated; API failure falls back to stale cache (`CACHE_FALLBACK`)
- [x] Source differentiation — `SyncSource` enum (`CACHE` / `API` / `CACHE_FALLBACK`) carried in `SyncState.Success`

**New files:** `data/sync/SyncState.kt`
**Key changes:** `ExerciseSyncService`, `SupabaseDataSource`, `SupabaseExerciseRepository`, `NavigationWrapper`, `HomeScreen`, `CategoryExercisesScreen`

---

### Phase 5 — Routine Persistence 🔄 NEXT PRIORITY

**Goal:** Save user routines to Supabase so they survive app restarts.

- [ ] Supabase `routines` and `routine_exercises` tables
- [ ] `RoutineRepository` with cloud CRUD
- [ ] Replace in-memory `RoutineState` with persistent state (or bridge pattern)
- [ ] Favorites / saved exercises system
- [ ] Exercise history stub

**Prerequisite:** Phase 6 (auth) needed for per-user routines. Can ship as shared anonymous routine first.

---

### Phase 6 — Authentication Layer ⏳ FUTURE

**Goal:** Add Supabase Auth so each user has their own cloud data.

- [ ] Supabase Auth: email/password login and register
- [ ] Session management in `commonMain`
- [ ] Protected user data (routines, history) per account
- [ ] Sync exercises and routines tied to authenticated user

---

### Phase 7 — Workout Logic Expansion ⏳ FUTURE

**Goal:** Make `WorkoutScreen` a real workout execution experience.

- [ ] Sets/reps/duration tracking per exercise
- [ ] Workout session state (`WorkoutSessionState` or ViewModel)
- [ ] Progress bar and exercise counter
- [ ] Rest timer
- [ ] Completion summary screen

---

### Phase 8 — Architecture Maturation ⏳ FUTURE

**Goal:** Migrate to production-grade state management where complexity warrants it.

- [ ] ViewModel + StateFlow for API-backed screens
- [ ] `UiState<T>` sealed class for loading/success/error states
- [ ] Platform-specific `WgerDataSource` for Android/Web (currently JVM-only)
- [ ] Dependency injection (Koin KMP) if needed

---

### Phase 9 — Full GymSpot Expansion 🚀 LONG TERM

- Maps integration (nearby gyms)
- Social routines
- AI exercise recommendations
- Workout analytics dashboard
- Monetization / premium features

---

## Scope Boundaries (Current MVP)

**Won't have until explicitly planned:**
- Social feed
- Payments / subscriptions
- Wearable integrations
- Real-time multiplayer
- Large-scale analytics

---

## Implementation Reference

- Architecture patterns: `.github/skills/implementation-patterns/SKILL.md`
- Agent rules and git workflow: `.github/agents/kotlin-developer.agent.md`
- Development conventions: `CLAUDE.md`
