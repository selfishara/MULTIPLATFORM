# GymSpot Lite — Project Plan

## Current Assessment (May 2026)

GymSpot Lite is a **portfolio-grade KMP fitness app** with production-style architecture. Real API integration, cloud persistence, user authentication, multi-routine management, and a full workout execution flow — all built with shared Kotlin code targeting Android, Desktop, Web, and iOS.

**What's working:**
- Clean layer separation: remote API → sync → Supabase → domain → UI
- KMP-first: all business logic in `commonMain`, platform specifics isolated
- Exercise browsing with real Wger API data, language switching (EN/ES)
- Multiple routines per user — create, name, manage, delete; all persisted in Supabase
- Favorites saved per user account
- Full workout execution: sets/reps tracking, rest timer, completion summary
- Supabase Auth: email/password, session persistence, per-user data isolation
- CI pipeline on GitHub Actions

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

### Phase 4 — Sync Optimization ✅ COMPLETED

- `existsByLanguage(language)` — prevents redundant Wger fetches
- Selective/partial sync per language
- Non-blocking loading via `SyncState` StateFlow
- Better error handling: Supabase write failures isolated, API failure falls back to stale cache

### Phase 5 — Routine Persistence ✅ COMPLETED

- Supabase `routines` and `routine_exercises` tables
- `RoutineRepository` with cloud CRUD
- `RoutineState` syncs every mutation to Supabase in real time
- Favorites system (`FavoriteDataSource`, `FavoritesState`, `FavoritesScreen`)
- Exercise history stub (`HistoryScreen` placeholder)

### Phase 6 — Authentication Layer ✅ COMPLETED

- Supabase Auth: email/password login and register (`AuthState`, `LoginScreen`)
- Session management in `commonMain` (`PersistentSessionManager`, `AuthTokenStore` expect/actual)
- User data (routines, favorites) isolated by `userId`
- Logout flow: clears local state, navigates to Login

### Phase 7 — Workout Logic Expansion ✅ COMPLETED

- Sets/reps/duration tracking per exercise (3 sets × 10 reps)
- `WorkoutSessionState`: currentIndex, setsCompleted, totalSeconds, restSecondsLeft
- Progress bar and exercise counter
- Rest timer — 60s countdown with skip, driven by `LaunchedEffect`
- Completion summary screen: time elapsed, exercises done, sets done

### Phase 7.5 — Multiple Routines ✅ COMPLETED

- `RoutinesScreen` — list of all user routines, create/delete
- `RoutinesListState` — Compose-observable list of all routines
- `RoutineRepository` extended: `loadAll`, `createNew`, `delete`
- Tap a routine → loads into `RoutineState` → opens `MyRoutineScreen`
- Create new routine → dialog for name → persisted to Supabase immediately
- Template apply (HomeScreen Quick Start) → creates a new named routine in Supabase
- Profile stat updated: shows total routine count → navigates to `RoutinesScreen`

---

### Phase 8 — Architecture Maturation 🚫 NOT IN THIS VERSION

- ViewModel + StateFlow for API-backed screens
- `UiState<T>` sealed class for loading/success/error
- Platform-specific `WgerDataSource` for Android/Web (currently JVM-only)
- Dependency injection (Koin KMP)

### Phase 9 — Full GymSpot Expansion 🚫 NOT IN THIS VERSION

- Maps integration (nearby gyms)
- Social routines
- AI exercise recommendations
- Workout analytics dashboard
- Monetization / premium features

---

## Scope Boundaries

**Out of scope for this delivery:**
- Social feed
- Payments / subscriptions
- Wearable integrations
- Real-time multiplayer
- Large-scale analytics
- ViewModel migration
- Android/Web WgerDataSource
