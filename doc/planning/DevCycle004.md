# DevCycle 004: Slant Rhyme Drill

**Status:** Work Complete
**Start Date:** 2026-04-25
**Target Completion:** 2026-04-25
**Focus:** Add a Slant Rhyme Drill as a new top-level feature, giving users a seed word and the option to reveal a slant rhyme example for practice.

---

## Goal

The app currently has two main practice modes — Improv Song and Rhyme Drill. Slant Rhyme Drill adds a third mode at the same level. It gives the user a seed word (e.g. "wake") and lets them either advance to the next seed word or reveal a pre-paired slant rhyme example (e.g. "wait"). Slant rhymes are near-rhymes where sounds are similar but not identical; exposing songwriters to them widens their rhyming vocabulary beyond perfect rhymes.

## Desired Outcome

A new "Slant Rhyme Drill" button appears on the Home screen alongside Improv Song and Rhyme Drill. Tapping it opens a drill screen that displays a seed word in a card. Two buttons are available: "Next Word" (advances to a new seed word, hides any revealed slant rhyme) and "Show Slant Rhyme" (reveals the paired slant rhyme example below the card). The slant rhyme data is stored separately from `WordData.kt` and is initialized with 20 seed/example pairs.

---

## Tasks

### Phase 1: Data

**Status:** Work Complete

- [x] Create `SlantRhymeData.kt` in the `data/` package
  - Define `data class SlantRhymePair(val seed: String, val example: String)`
  - Populate `val allPairs: List<SlantRhymePair>` with 20 seed/example pairs

**Technical Notes:**
`SlantRhymeData.kt` follows the same pattern as `WordData.kt` — a plain `object` with a static list. No filtering logic is needed at this stage; all 20 pairs are always in the pool.

---

### Phase 2: ViewModel

**Status:** Work Complete

- [x] Create `SlantRhymeViewModel.kt` in the `viewmodel/` package
  - `currentPair: StateFlow<SlantRhymePair?>` — the active seed/example pair
  - `isExampleVisible: StateFlow<Boolean>` — whether the slant rhyme example is currently shown
  - `nextWord()` — picks a new random pair (avoiding the current one), sets `isExampleVisible` to `false`
  - `showExample()` — sets `isExampleVisible` to `true`

**Technical Notes:**
No `Application` context or settings dependency is needed for the initial implementation — the pool is a simple in-memory list and there is no "avoid repeats" setting for this mode yet. Use a plain `ViewModel` (not `AndroidViewModel`) for simplicity.

A `LaunchedEffect(Unit)` in the screen will call `nextWord()` on first entry, same pattern as `RhymeDrillScreen`.

---

### Phase 3: Screen and Navigation

**Status:** Work Complete

- [x] Create `SlantRhymeDrillScreen.kt` in the `ui/screens/` package
  - `Scaffold` with `TopAppBar` ("Slant Rhyme Drill") and back navigation arrow
  - Seed word displayed in a `Card` matching `RhymeDrillScreen`'s style
  - Below the card: slant rhyme example text, visible only when `isExampleVisible == true`
  - "Next Word" button — calls `viewModel.nextWord()`
  - "Show Slant Rhyme" button — calls `viewModel.showExample()`, hidden once example is visible
- [ ] Add `SlantRhymeDrill : Screen("slant_rhyme_drill")` to `Screen.kt`
- [ ] Add `composable(Screen.SlantRhymeDrill.route)` destination to `AppNavigation.kt`
- [ ] Add `onSlantRhymeDrillClick` parameter and "Slant Rhyme Drill" `Button` to `HomeScreen.kt`
- [ ] Wire the new button in `AppNavigation.kt`'s `HomeScreen` composable call

**Technical Notes:**
`SlantRhymeDrillScreen` is modeled on `RhymeDrillScreen` but replaces the Easy/Normal toggle with a "Show Slant Rhyme" button. The revealed example should appear in a secondary `Text` below the main card, styled with `MaterialTheme.typography.headlineMedium` and `colorScheme.secondary` to visually distinguish it from the seed word. The "Show Slant Rhyme" button should be hidden (not just disabled) once the example is visible, so the only action at that point is "Next Word".

The `HomeScreen` composable currently has three parameters: `onImprovClick`, `onRhymeDrillClick`, `onSettingsClick`. Adding `onSlantRhymeDrillClick` as a fourth keeps the pattern consistent. Place the new button between Rhyme Drill and the Settings text link.

---

## Notes and Risks

- No new dependencies are required — this feature uses only existing Compose, ViewModel, and navigation infrastructure.
- `SlantRhymeData` is intentionally separate from `WordData` so the two pools don't interfere with each other.
- "Show Slant Rhyme" hiding itself after tap is a UX choice: it prevents re-tapping and makes "Next Word" the only forward action, mirroring how Rhyme Drill has a single primary action at any moment.

---

## Completion Summary

**Completion Date:** 2026-04-25
**Phases Completed:** All (1–3)
**Work Deferred:** None

**Accomplishments:**
- Created `SlantRhymeData.kt` with `SlantRhymePair` data class and 20 seed/example pairs
- Created `SlantRhymeViewModel.kt` with `currentPair`, `isExampleVisible` state flows and `nextWord()`/`showExample()` actions
- Created `SlantRhymeDrillScreen.kt` — seed word card, revealed example text, "Show Slant Rhyme" / "Next Word" buttons
- Added `SlantRhymeDrill` route to `Screen.kt`
- Added composable destination and Home wiring to `AppNavigation.kt`
- Added `onSlantRhymeDrillClick` parameter and "Slant Rhyme Drill" button to `HomeScreen.kt`

**Metrics:**
- Files created: 3 (`SlantRhymeData.kt`, `SlantRhymeViewModel.kt`, `SlantRhymeDrillScreen.kt`)
- Files modified: 3 (`Screen.kt`, `AppNavigation.kt`, `HomeScreen.kt`)

**Lessons / Notes:**
Plain `ViewModel` (not `AndroidViewModel`) works here since there's no settings dependency — keeps the class simpler. "Show Slant Rhyme" button hides itself after tap so "Next Word" becomes the only forward action, avoiding a redundant second tap.
