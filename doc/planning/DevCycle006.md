# DevCycle 006: Loose Rhymes – Hard Ending Shift

**Status:** Work Complete
**Start Date:** 2026-04-25
**Target Completion:** 2026-04-26
**Focus:** Introduce a "Loose Rhymes" category with a subcategory screen, and add Hard Ending Shift as the first new subcategory under it.

---

## Goal

The app currently exposes Slant Rhyme Drill as a standalone home-screen feature. This cycle restructures that entry point into a "Loose Rhymes" category — a home for all near-rhyme practice modes — and adds **Hard Ending Shift** as the first named subcategory. Hard Ending Shift pairs share a vowel sound but shift the ending consonant, giving songwriters a distinct, teachable variation to practice.

## Desired Outcome

The Home screen has a "Loose Rhymes" button. Tapping it opens a subcategory selection screen with three options: **Default** (existing Slant Rhyme Drill), **All** (deferred), and **Hard Ending Shift**. Tapping Hard Ending Shift shows a description screen explaining the pattern with example pairs and a "Start Drill" button. The drill shows a prompt word; tapping "Show Example" reveals the one predefined example pair; tapping "Next Word" advances and resets the example state.

---

## Tasks

### Phase 1: Data

**Status:** Work Complete

- [x] Create `HardEndingShiftData.kt` in the `data/` package
  - Define `data class HardEndingShiftPair(val wordA: String, val wordB: String)`
    - Note: either member of a pair can serve as the drill word; the ViewModel randomly assigns which is shown as seed vs example
  - Populate `val allPairs: List<HardEndingShiftPair>` with the 15 starter pairs from the design doc

**Technical Notes:**
Follows the same `object` pattern as `SlantRhymeData.kt`. No filtering or randomization logic lives in the data layer. Starter pairs:

```
bad→cat, cat→map, bed→set, dog→top, sun→cup,
book→put, stone→note, grass→cap, rain→late,
plate→make, road→note, made→take, side→time,
red→bet, mud→cut
```

---

### Phase 2: ViewModel

**Status:** Work Complete

- [x] Create `HardEndingShiftViewModel.kt` in the `viewmodel/` package
  - `currentPair: StateFlow<HardEndingShiftPair?>` — the active prompt/example pair
  - `isExampleVisible: StateFlow<Boolean>` — whether the example is currently shown
  - `nextWord()` — picks a new random pair (avoiding the current one), resets `isExampleVisible` to `false`
  - `showExample()` — sets `isExampleVisible` to `true`

**Technical Notes:**
Plain `ViewModel` (not `AndroidViewModel`) — no settings dependency at this stage. Same structure as `SlantRhymeViewModel.kt`. A `LaunchedEffect(Unit)` in the drill screen calls `nextWord()` on first entry.

---

### Phase 3: Hard Ending Shift Screens

**Status:** Work Complete

- [x] Create `HardEndingShiftDescriptionScreen.kt` in `ui/screens/`
  - `TopAppBar` with back navigation and title "Hard Ending Shift"
  - Description text block explaining the pattern
  - Example pairs displayed as a short list (cod→pot, bad→cat, bed→step)
  - "Start Drill" `Button` that navigates to the drill screen
- [x] Create `HardEndingShiftDrillScreen.kt` in `ui/screens/`
  - `TopAppBar` with back navigation and title "Hard Ending Shift"
  - Prompt word in a `Card` matching `SlantRhymeDrillScreen`'s style
  - Example text below the card, visible only when `isExampleVisible == true`
  - "Show Example" button — calls `viewModel.showExample()`, hidden once example is visible
  - "Next Word" button — calls `viewModel.nextWord()`

**Technical Notes:**
`HardEndingShiftDrillScreen` mirrors `SlantRhymeDrillScreen` exactly in layout. The description screen is a new pattern: a static info screen with a CTA. Keep it simple — a `Column` with `Text` blocks and a full-width `Button` at the bottom. No scroll needed given the content length.

---

### Phase 4: Subcategory Screen and Navigation Restructuring

**Status:** Work Complete

- [x] Add routes to `Screen.kt`:
  - `LooseRhymes : Screen("loose_rhymes")`
  - `HardEndingShiftDescription : Screen("hard_ending_shift_description")`
  - `HardEndingShiftDrill : Screen("hard_ending_shift_drill")`
- [x] Create `LooseRhymesSubcategoryScreen.kt` in `ui/screens/`
  - `TopAppBar` with back navigation and title "Loose Rhymes"
  - Three buttons: **Default**, **All** (disabled — coming soon), **Hard Ending Shift**
  - **Default** navigates to `Screen.SlantRhymeDrill`
  - **Hard Ending Shift** navigates to `Screen.HardEndingShiftDescription`
- [x] Update `AppNavigation.kt`:
  - Add `composable(Screen.LooseRhymes.route)` → `LooseRhymesSubcategoryScreen`
  - Add `composable(Screen.HardEndingShiftDescription.route)` → `HardEndingShiftDescriptionScreen`
  - Add `composable(Screen.HardEndingShiftDrill.route)` → `HardEndingShiftDrillScreen`
- [x] Update `HomeScreen.kt`:
  - Renamed "Slant Rhyme Drill" button label to **"Loose Rhymes"**
  - Renamed `onSlantRhymeDrillClick` to `onLooseRhymesClick`, navigates to `Screen.LooseRhymes`

**Technical Notes:**
The existing `SlantRhymeDrill` route and screen are unchanged — only the home button's label and destination change. `AppNavigation.kt`'s existing `HomeScreen` composable call passes `onSlantRhymeDrillClick`; rename this parameter to `onLooseRhymesClick` for clarity and update all three files (`HomeScreen.kt`, `AppNavigation.kt`, and the call site) together.

The **All** button can be shown but disabled (`enabled = false`) with a note like "Coming soon" to communicate the intent without requiring implementation this cycle.

---

## Open Questions

1. **Should "All" mode be completely hidden or shown as disabled?**
   Recommendation: Show it as disabled with a "Coming soon" label. This communicates the roadmap to users without implying it's broken, and costs nothing to implement.

2. **Should the Home button rename from "Slant Rhyme Drill" to "Loose Rhymes" break the existing navigation contract?**
   Recommendation: Yes — rename cleanly. The old `SlantRhymeDrill` route is preserved as the "Default" destination; only the home entry point changes. No backwards-compat shim is needed since there are no deep links.

---

## Notes and Risks

- Renaming `onSlantRhymeDrillClick` to `onLooseRhymesClick` touches `HomeScreen.kt`, `AppNavigation.kt`, and any call site — all are in the same module, so this is low risk.
- No new dependencies required.
- `HardEndingShiftData.kt` is intentionally separate from `SlantRhymeData.kt`; the "All" mode will aggregate them in a future cycle.

---

## Completion Summary

**Completion Date:** 2026-04-25
**Phases Completed:** All (1–4)
**Work Deferred:** None

**Accomplishments:**
- Created `HardEndingShiftData.kt` with `HardEndingShiftPair` data class and 15 starter pairs
- Created `HardEndingShiftViewModel.kt` — randomly assigns which word is seed vs example, matching `SlantRhymeViewModel` pattern
- Created `HardEndingShiftDescriptionScreen.kt` — description, example list, "Start Drill" CTA
- Created `HardEndingShiftDrillScreen.kt` — prompt card, "Show Example" / "Next Word" buttons
- Created `LooseRhymesSubcategoryScreen.kt` — Default, All (disabled), Hard Ending Shift buttons
- Added `LooseRhymes`, `HardEndingShiftDescription`, `HardEndingShiftDrill` routes to `Screen.kt`
- Updated `AppNavigation.kt` with three new composable destinations
- Updated `HomeScreen.kt`: renamed param to `onLooseRhymesClick`, button label to "Loose Rhymes"

**Metrics:**
- Files created: 5 (`HardEndingShiftData.kt`, `HardEndingShiftViewModel.kt`, `HardEndingShiftDescriptionScreen.kt`, `HardEndingShiftDrillScreen.kt`, `LooseRhymesSubcategoryScreen.kt`)
- Files modified: 3 (`Screen.kt`, `AppNavigation.kt`, `HomeScreen.kt`)

**Lessons / Notes:**
Used symmetric `wordA`/`wordB` field names instead of `prompt`/`example` in the data class — matches `SlantRhymePair` and correctly reflects that either word can be the drill seed. The ViewModel handles the presentational assignment at runtime.
