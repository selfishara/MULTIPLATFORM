# [AGENTS.md](http://agents.md/) — GymSpot Lite / Kotlin Multiplatform Exercise Platform

## Project Overview

**Project Name:** GymSpot Lite (KMP MVP)

**Owner:** selfishara

**Repository Type:** Kotlin Multiplatform / Clean Architecture / Portfolio-grade production-style MVP

GymSpot Lite is a scalable cross-platform fitness application focused on:

- Exercise discovery
- Multilingual exercise catalogs
- Persistent cloud caching
- Routine creation
- Expandable architecture for future:
    - Social features
    - Shared routines
    - Progress tracking
    - Maps integration
    - Premium product growth

---

# Current Development Level

## Stage:

### MVP+ (Advanced Educational / Early Portfolio Production)

This is no longer a simple practice app.
Current system includes:

- Real external API integration (Wger)
- Supabase cloud persistence
- Language filtering
- Kotlin Multiplatform architecture
- Desktop + Android support
- Navigation system
- Routine system foundation
- Expandable repository pattern

---

# Core Technologies

## Frontend/UI:

- Kotlin Multiplatform
- Jetpack Compose Multiplatform
- Material 3
- Navigation 3

## Backend/Data:

- Wger API
- Supabase
- PostgREST
- PostgreSQL

## Architecture:

- MVVM
- Repository Pattern
- DataSource Layer
- Sync Service Layer
- DTO + Mapper Pattern
- Feature-based modular structure

## Tooling:

- Android Studio
- Visual Studio Code
- Git
- GitHub
- Supabase
- Cloud Code / Chat GPT paid subscription
- other (design .md / skills.md...)

---

# Mandatory Agent Behavior Rules

## CRITICAL:

### NEVER modify code directly without explicit user approval.

Before:

- Editing files
- Refactoring architecture
- Deleting classes
- Renaming packages
- Changing schemas
- Git actions

### MUST:

1. Analyze
2. Explain
3. Propose
4. Wait for user approval

---

# Git Workflow Rules

## Branching:

- NEVER work directly on `main`
- ALWAYS create feature branch
- Branch naming:


feature/<feature-name>
fix/<bug-name>
refactor/<scope>

Commit Style:

feat:
fix:
refactor:
docs:
chore:

Before PR:

* Build must pass
* Desktop test required
* Android compatibility preserved
* No architecture regression

⸻

Development Phases

Phase 1 — Core Exercise API Integration

Status: ✅ COMPLETED

Includes:

* Wger API integration
* DTO creation
* Mapper system
* Pagination
* Initial 200 exercise fetch
* Muscle group categorization
* Language support
* Category screens

⸻

Phase 2 — Language Filtering System

Status: ✅ COMPLETED

Includes:

* English/Spanish language model
* Preferred language filtering
* Translation completeness validation
* Invalid exercise exclusion
* API language ID handling

⸻

Phase 3 — Persistent Supabase Cache

Status: ✅ COMPLETED (Current milestone)

Includes:

* Supabase project setup
* PostgreSQL exercise table
* Supabase client provider
* Supabase DTO
* Supabase mapper
* Supabase repository
* Exercise sync service
* Cloud cache fallback:

Supabase → Wger → Supabase sync

Current Result:

* 192 exercises stored
* Faster future loads
* Reduced API dependency
* Production-style architecture

⸻

Phase 4 — Sync Optimization

Status: 🔄 CURRENT NEXT PRIORITY

Goals:

* Prevent duplicate syncs
* Check language existence before fetch
* Partial sync by language
* Background sync improvements
* Better error handling
* Source differentiation

Must Implement:

* existsByLanguage()
* Selective sync
* Smart cache validation

⸻

Phase 5 — User Routine Expansion

Status: ⏳ UPCOMING

Goals:

* Save routines in Supabase
* User profile linkage
* Routine CRUD
* Favorites system
* Exercise history

⸻

Phase 6 — Authentication Layer

Status: ⏳ FUTURE

Goals:

* Supabase Auth
* Login/Register
* Cloud sync per user
* Saved progress
* Protected user data

⸻

Phase 7 — Full GymSpot Expansion

Status: 🚀 LONG TERM

Potential:

* Maps
* Nearby gyms
* Social routines
* AI recommendations
* Workout analytics
* Monetization
* Premium features

⸻

Product Priorities

MUST HAVE:

* Stable architecture
* Clean repositories
* Sync reliability
* Multilingual exercise support
* Cloud persistence
* Routine system
* Git discipline

⸻

SHOULD HAVE:

* Authentication
* Favorites
* Offline mode
* Better UI polish
* Search/filter system
* Advanced sync metrics

⸻

WON’T HAVE (Current MVP Scope):

* Social feed
* Payments
* Premium subscriptions
* Wearable integrations
* Real-time multiplayer
* AI coaching agents
* Large-scale analytics dashboards

⸻

Limitations

Current:

* No user auth yet
* Shared database cache only
* No personalized data
* Limited language support
* Basic sync logic
* No automated tests yet
* Desktop-first testing
* Mobile optimization pending

⸻

Agent Coding Standards

Required:

* Preserve package structure
* Preserve KMP compatibility
* Prefer extension over replacement
* Document major architecture changes
* Keep code production-scalable
* Follow existing style

Forbidden:

* Random refactors
* Direct main edits
* Silent deletions
* Schema breaks
* Overengineering beyond current phase

⸻

Repository Awareness

Agent should understand:

Key directories:

data/
 ├── remote/
 ├── supabase/
 ├── sync/
model/
navigation/
screens/
components/
state/

⸻

Collaboration Style

Preferred:

* Senior architect assistant
* Explain before acting
* Portfolio-aware decisions
* Scale-conscious
* Educational support

⸻

Final Directive

This project is both:

Educational

AND

Portfolio-grade

Therefore all implementations must balance:

* Learning clarity
* Real-world architecture
* Recruiter visibility
* Scalability
* Code cleanliness

⸻

Owner Preferences

* Branch-first workflow
* Detailed explanations
* Cloud Code integration
* Local LLM compatibility
* Android + Desktop support
* Professional engineering practices

⸻

END OF AGENT CONFIG
```