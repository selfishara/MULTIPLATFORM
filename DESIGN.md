# DESIGN.md — GymSpot Lite

Design system reference for GymSpot Lite. All UI work must follow these rules.

---

## Identity

GymSpot Lite feels like a **focused training tool**, not a social app or a generic dashboard.

**Direction:** dark-first, high contrast, athletic minimalism.
Every screen has one job. No visual noise. No decorative filler.

**Personality:** structured, confident, direct. Like a good coach — no fluff.

---

## Color Palette

### Dark scheme (primary)

| Token | Hex | Usage |
|---|---|---|
| Primary | `#FF6B35` | CTAs, active states, key highlights |
| OnPrimary | `#FFFFFF` | Text/icons on primary |
| PrimaryContainer | `#3D1A0A` | Card backgrounds with primary tint |
| OnPrimaryContainer | `#FFB49A` | Text/icons inside primary containers |
| Secondary | `#B0B8C1` | Secondary actions, subtitles |
| OnSecondary | `#1A1E22` | Text on secondary |
| SecondaryContainer | `#2A2F35` | Chip backgrounds, secondary cards |
| OnSecondaryContainer | `#D8DDE3` | Text on secondary containers |
| Background | `#0F1013` | App background |
| OnBackground | `#E8EAED` | Primary text |
| Surface | `#1A1D21` | Cards, sheets, dialogs |
| OnSurface | `#E8EAED` | Text on surface |
| SurfaceVariant | `#252830` | Input fields, list separators |
| OnSurfaceVariant | `#9AA0A8` | Placeholder text, secondary labels |
| Error | `#FF5449` | Error states |
| OnError | `#FFFFFF` | Text on error |
| ErrorContainer | `#3B0F0C` | Error banners |
| OnErrorContainer | `#FFBAB5` | Text on error containers |
| Outline | `#3C4048` | Borders, dividers |

### Light scheme (secondary / optional)

| Token | Hex | Usage |
|---|---|---|
| Primary | `#C94E1A` | CTAs (warmer on light) |
| Background | `#F5F5F5` | App background |
| Surface | `#FFFFFF` | Cards |
| OnBackground | `#111213` | Primary text |
| OnSurface | `#1A1D21` | Text on cards |

---

## Typography

Use the platform default sans-serif. No custom font imports unless explicitly added.

| Role | Weight | Size | Usage |
|---|---|---|---|
| displayLarge | Bold | 32sp | Workout completion, hero numbers |
| headlineMedium | SemiBold | 24sp | Screen titles, routine name |
| titleLarge | SemiBold | 20sp | Section headers, card titles |
| titleMedium | Medium | 16sp | List item names, exercise titles |
| bodyLarge | Regular | 16sp | Body copy, descriptions |
| bodyMedium | Regular | 14sp | Secondary descriptions, subtitles |
| labelLarge | Medium | 14sp | Button text |
| labelSmall | Medium | 11sp | Tags, stat pill labels, captions |

**Rules:**
- Never use more than 3 type sizes on the same screen
- Titles are always `onBackground` or `onSurface`
- Secondary text is always `onSurfaceVariant`
- No italic anywhere

---

## Spacing

Base unit: **4dp**

| Name | Value | Usage |
|---|---|---|
| xs | 4dp | Icon internal padding |
| sm | 8dp | Between tightly related elements |
| md | 12dp | Card internal vertical gaps |
| lg | 16dp | Screen horizontal padding, list spacing |
| xl | 20dp | Card padding |
| xxl | 24dp | Section gaps |
| hero | 32dp | Top-level screen padding |

Screen horizontal padding: always **16dp**.
Card corner radius: **16dp** standard, **24dp** for hero cards, **999dp** for pills/chips.

---

## Components

### TopBar
- Background: `Background` (transparent feel)
- Title: `titleLarge`, `onBackground`
- Icons: `onSurfaceVariant`, 24dp, touch target 48dp
- No elevation/shadow
- Always show back arrow when not on root

### Card (standard)
- Background: `Surface`
- Corner: 16dp
- Padding: 16–20dp
- No border unless in selected state
- Selected state: `Primary` border 1.5dp + `PrimaryContainer` background tint

### Card (hero / featured)
- Background: `PrimaryContainer`
- Corner: 24dp
- Padding: 22dp
- Used for: HomeScreen main card, routine header

### Button (primary)
- Background: `Primary`
- Text: `OnPrimary`, labelLarge, no uppercase
- Corner: 999dp (fully rounded)
- Height: 48dp
- No shadow

### Button (outlined)
- Border: `Outline` 1dp
- Text: `OnBackground`, labelLarge
- Corner: 999dp
- Height: 48dp

### StatPill
- Background: `OnPrimaryContainer` @ 12% alpha
- Text: `OnPrimaryContainer`
- Corner: 999dp
- Used only inside hero cards

### ExerciseCard / ExerciseItem
- Background: `Surface`
- Corner: 12dp
- Padding: 14dp horizontal, 12dp vertical
- Name: `titleMedium`, `onSurface`
- Muscle group: `labelSmall`, `onSurfaceVariant`
- Remove button: icon only, `onSurfaceVariant`, right-aligned

### CategoryCard
- Background: `SecondaryContainer`
- Icon: `Primary`, centered, 32dp
- Label: `labelLarge`, `onSecondaryContainer`, centered below icon
- Corner: 16dp
- Aspect ratio: ~1:1.1
- On hover/press: `Primary` border 1.5dp

### CircularProgressIndicator (loading)
- Color: `Primary`
- Size: 32dp, strokeWidth 3dp (category screens)
- Size: 20dp, strokeWidth 2dp (inline / home)

### Error banner
- Background: `ErrorContainer`
- Text: `OnErrorContainer`, bodyMedium
- Corner: 16dp
- Padding: 12dp horizontal, 10dp vertical
- No icon unless space allows

### SyncSource badge (optional)
- Background: `SecondaryContainer`
- Text: `OnSecondaryContainer`, labelSmall
- Corner: 999dp
- Shown briefly after load, fades out

---

## Screen Principles

### HomeScreen
- Hero card occupies top ~40% of viewport
- Stat pills inline with language buttons — same row
- Two action cards below, equal weight
- No scrolling unless content overflows

### ExercisesScreen (Categories)
- 3-column grid, no horizontal scroll
- Cards fill width evenly with 12dp gap
- No list view alternative — grid only

### CategoryExercisesScreen
- Full-width list, 12dp gap between items
- Empty/loading states are centered vertically, not top-aligned
- Loading: spinner only — no text alongside it

### ExerciseDetailScreen
- Exercise name: `headlineMedium`
- Muscle group chip: `SecondaryContainer` pill
- Instructions block: `bodyMedium`, `onSurfaceVariant`, scrollable
- Add button: full-width primary, pinned to bottom

### MyRoutineScreen
- Routine name input: outlined, no fill
- Exercise count: `bodyMedium`, `onPrimaryContainer`, inside header card
- Start workout: primary full-width, disabled when list empty
- Clear button: outlined, destructive label, bottom of list

### WorkoutScreen
- Dark background, maximum focus
- Current exercise: `headlineMedium`, center-aligned
- Progress: single progress indicator, top of screen
- Next/done controls: large touch targets (56dp height minimum)

---

## Animation & Motion

- Screen transitions: fade, 200ms, `FastOutSlowIn`
- List item appear: no animation (instant, avoids jank on long lists)
- Loading → content: crossfade 150ms
- Button press: ripple only (Material default)
- No bouncy springs, no complex path animations

---

## Anti-patterns

- No gradients on backgrounds or buttons
- No card shadows (`elevation = 0dp` everywhere)
- No full-screen modals for simple actions — use in-screen state
- No floating action buttons (FAB)
- No bottom navigation bar until the app has 4+ top-level destinations
- No purple, no teal, no generic blue-gray palettes
- No stock illustration assets or emoji in UI
- No placeholder text left visible in production screens (`totalExercises = 0` on HomeScreen must be fixed)
