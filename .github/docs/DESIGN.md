# DESIGN.md — GymSpot Lite
### Inspired by Nike Podium CDS — adapted for Kotlin Compose Multiplatform

---

## 1. Visual Identity

GymSpot Lite is a **kinetic training tool**. The design operates on the same principle as Nike.com: radical simplicity so that athletic content dominates without competition. Strip the UI to near-black, near-white, and one orange accent — then let exercise imagery and workout data carry the energy.

**Dark-first.** Nike runs light; GymSpot runs dark — the gym is a dark room, a pre-dawn run, a focused headspace. The same monochromatic discipline applies, inverted.

**One accent color. No exceptions.** `#FF5000` (Nike Orange Flash) handles every CTA, active state, and highlight. Everything else is grey.

**Typography punches.** Upper-case, heavy-weight display text for hero moments. No italic, no decorative fonts.

**Zero elevation.** No card shadows, no lifted surfaces. Depth through colour shifts only.

---

## 2. Color Palette

### Dark scheme (primary — always use this)

| Token (Material 3) | Hex | Role |
|---|---|---|
| `primary` | `#FF5000` | CTAs, active icons, key highlights — Nike Orange Flash |
| `onPrimary` | `#FFFFFF` | Text/icons on orange |
| `primaryContainer` | `#3A1600` | Cards with orange tint, hero card background |
| `onPrimaryContainer` | `#FFB59A` | Text inside hero cards |
| `secondary` | `#9AA0A8` | Secondary labels, subtitles, inactive icons |
| `onSecondary` | `#0F0F0F` | Text on secondary |
| `secondaryContainer` | `#28282A` | Chips, category cards, secondary surfaces |
| `onSecondaryContainer` | `#D8DDE3` | Text inside secondary containers |
| `background` | `#0F0F0F` | App background — near-black, never pure black |
| `onBackground` | `#FAFAFA` | Primary text — near-white |
| `surface` | `#1A1A1A` | Cards, sheets, list items |
| `onSurface` | `#FAFAFA` | Text on cards |
| `surfaceVariant` | `#252528` | Input fields, list separators |
| `onSurfaceVariant` | `#707072` | Placeholder text, secondary labels |
| `outline` | `#3C3C3E` | Borders, dividers — 1dp only |
| `error` | `#D30005` | Errors — Nike Red |
| `onError` | `#FFFFFF` | Text on error |
| `errorContainer` | `#3B0000` | Error banners |
| `onErrorContainer` | `#FFB3AE` | Text on error banner |

### State grey scale (for hover/press/disabled — no extra colours)

| State | Hex | Usage |
|---|---|---|
| Hover surface | `#252528` | Row hover, card press |
| Disabled fill | `#2A2A2D` | Disabled button background |
| Disabled text | `#4B4B4D` | Disabled text |
| Divider | `#3C3C3E` | 1dp horizontal rules |

**Rule:** if you are tempted to introduce a new colour, use a grey instead.

---

## 3. Typography

No custom font imports. Use `FontWeight` + `TextDecoration` in Compose to achieve the Nike typographic energy with system fonts.

| Material 3 style | Weight | Size | Case | Usage |
|---|---|---|---|---|
| `displayLarge` | ExtraBold (800) | 32sp | UPPERCASE | Workout completion hero, screen-defining moments |
| `headlineMedium` | Bold (700) | 24sp | Title Case | Screen titles, routine name, exercise name in detail |
| `titleLarge` | SemiBold (600) | 20sp | Title Case | Card headers, section titles |
| `titleMedium` | Medium (500) | 16sp | Title Case | Exercise name in list, category label |
| `bodyLarge` | Normal (400) | 16sp | Sentence | Instructions, descriptions |
| `bodyMedium` | Normal (400) | 14sp | Sentence | Secondary copy, subtitles |
| `labelLarge` | Medium (500) | 14sp | Sentence | Button text — never uppercase on buttons |
| `labelSmall` | Medium (500) | 11sp | UPPERCASE | Stat pill labels, tags, captions |

**Rules:**
- Max 3 type sizes per screen
- `displayLarge` uppercase only for true hero moments — workout done, first load
- `onBackground` / `onSurface` for all primary text
- `onSurfaceVariant` (`#707072`) for all secondary text — never go lighter
- No italic anywhere

---

## 4. Spacing

Base unit: **4dp**. All values snap to 4dp multiples.

| Token | Value | Usage |
|---|---|---|
| `xs` | 4dp | Icon internal padding, tight inline gaps |
| `sm` | 8dp | Between related inline elements |
| `md` | 12dp | Card internal vertical rhythm |
| `lg` | 16dp | Screen horizontal padding, list item gaps |
| `xl` | 20dp | Card padding |
| `xxl` | 24dp | Section internal padding |
| `section` | 32dp | Between major sections |
| `hero` | 48dp | Hero section top padding |

Screen horizontal padding: **always 16dp**.

---

## 5. Shape (Border Radius)

| Value | Context |
|---|---|
| 0dp | Full-bleed imagery (no images yet — reserved) |
| 8dp | Input fields |
| 16dp | Standard cards, exercise items |
| 24dp | Hero cards, large feature cards |
| 999dp | Buttons (full pill), chips, stat pills |

**Rule:** buttons are always full-pill (999dp). Cards are 16dp or 24dp. No intermediate values.

---

## 6. Elevation & Depth

Zero shadows. No `elevation` values on cards or surfaces.

| Layer | Treatment | Usage |
|---|---|---|
| Flat | No shadow, no border | Default for everything |
| Divider | `outline` colour, 1dp | Subtle section separation |
| Focus ring | `primary` colour, 2dp | Focused input, keyboard nav |

Depth is communicated exclusively through colour shifts:
- `background` `#0F0F0F` → furthest back
- `surface` `#1A1A1A` → content layer
- `surfaceVariant` `#252528` → elevated/interactive elements
- `primaryContainer` `#3A1600` → featured/hero content

---

## 7. Components

### Button — Primary
- Background: `primary` `#FF5000`
- Text: `onPrimary`, `labelLarge`, sentence case
- Shape: 999dp (full pill)
- Height: 48dp
- No shadow, no border
- Disabled: `#2A2A2D` background, `#4B4B4D` text

### Button — Outlined
- Background: transparent
- Border: 1dp `outline` `#3C3C3E`
- Text: `onBackground`, `labelLarge`
- Shape: 999dp
- Height: 48dp
- Hover/press: border shifts to `onSurfaceVariant` `#707072`

### TopBar
- Background: `background` (no separate bar colour — blends with screen)
- Title: `titleLarge`, `onBackground`
- Icons: `onSurfaceVariant`, 24dp, touch target 48dp
- No elevation, no divider unless screen scrolls underneath

### Card — Hero (HomeScreen, routine header)
- Background: `primaryContainer` `#3A1600`
- Shape: 24dp
- Padding: 22dp
- Text: `onPrimaryContainer`
- One per screen maximum

### Card — Standard
- Background: `surface` `#1A1A1A`
- Shape: 16dp
- Padding: 16dp
- Text: `onSurface`
- No border in default state
- Selected: 1.5dp `primary` border

### ExerciseItem (routine list row)
- Background: `surface`
- Shape: 16dp
- Padding: 14dp horizontal, 12dp vertical
- Name: `titleMedium`, `onSurface`
- Muscle group: `labelSmall` uppercase, `onSurfaceVariant`
- Remove: icon only, `onSurfaceVariant`, trailing end

### CategoryCard (3-col grid)
- Background: `secondaryContainer` `#28282A`
- Icon: `primary` `#FF5000`, 32dp, centred
- Label: `labelLarge`, `onSecondaryContainer`, centred below icon
- Shape: 16dp
- Ratio: ~1:1.1
- Press: 1.5dp `primary` border appears

### StatPill
- Background: `onPrimaryContainer` @ 12% alpha (inside hero cards only)
- Text: `onPrimaryContainer`, `labelSmall` uppercase
- Shape: 999dp
- Padding: 10dp vertical, 14dp horizontal

### Chip / Tag
- Background: `secondaryContainer`
- Text: `onSecondaryContainer`, `labelSmall` uppercase
- Shape: 999dp

### Input field (routine rename)
- Background: transparent
- Border: 1dp `outline`, 8dp shape
- Focus border: `primary`
- Text: `onSurface`, `bodyLarge`
- Label: `onSurfaceVariant`, `bodyMedium`

### Error banner
- Background: `errorContainer`
- Text: `onErrorContainer`, `bodyMedium`
- Shape: 16dp
- Padding: 12dp horizontal, 10dp vertical

### Loading indicator
- Colour: `primary` `#FF5000`
- Screen-centred: 32dp, strokeWidth 3dp
- Inline (HomeScreen stat row): 20dp, strokeWidth 2dp

---

## 8. Screen Principles

### HomeScreen
- Hero card: `primaryContainer`, 24dp shape, top ~40% of screen
- Stat pills inside hero — inline with language buttons
- Two action cards below: equal weight, `surface` background
- Language selector: two standard buttons — active one disabled
- No FAB

### ExercisesScreen (Categories)
- 3-column fixed grid, 12dp gap
- Only `CategoryCard` components — no list view
- No hero, no banner

### CategoryExercisesScreen
- Full-width list, 12dp vertical gap between items
- Loading: centred spinner only — no text
- Empty: centred card, single title + single body line

### ExerciseDetailScreen
- Exercise name: `headlineMedium`, `onBackground`
- Muscle group: chip, `secondaryContainer`
- Instructions: `bodyMedium`, `onSurfaceVariant`, scrollable
- Add to routine button: full-width primary, pinned bottom, 16dp margin

### MyRoutineScreen
- Header card: `primaryContainer` hero card
- Rename input inside header card
- Exercise count: `bodyMedium`, `onPrimaryContainer`
- Start workout: primary full-width, disabled when list empty
- Exercise list: `ExerciseItem` components
- Clear: outlined button, bottom of list

### WorkoutScreen
- Full `background` — maximum focus, no distractions
- Current exercise: `headlineMedium`, centre-aligned
- Progress: single linear indicator top of screen, `primary` colour
- Next/done: primary full-width, 56dp height minimum

---

## 9. Motion

- Screen transitions: fade, 200ms
- Loading → content: crossfade 150ms
- No spring animations, no bouncing
- Button press: Material ripple only
- List items: no entry animation (instant render)

---

## 10. Anti-patterns

- No card shadows (`elevation = 0` everywhere)
- No gradients on UI elements (only allowed on future hero photography overlays)
- No FAB
- No bottom navigation (fewer than 4 top-level destinations)
- No new colours — extend the grey scale instead
- No italic text
- No `displayLarge` uppercase below 24sp — it's only for hero moments
- No placeholder values visible in production (`exerciseCount = 0` in HomeScreen stat pill must be replaced by the real count or a spinner)
- No coloured backgrounds on UI elements — colour belongs to content, not chrome
