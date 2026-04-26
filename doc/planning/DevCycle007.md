# DevCycle 007: Loose Rhymes – Multi-Syllable

**Status:** Work Complete
**Start Date:** 2026-04-25
**Target Completion:** 2026-04-26
**Focus:** Add Multi-Syllable as a second subcategory under Loose Rhymes, with its own description screen, drill screen, and dataset.

---

## Goal

Hard Ending Shift (DC6) targets single-syllable words. Multi-Syllable extends Loose Rhymes into longer words where the goal is matching rhythm, vowel flow, and stress pattern rather than a single sound shift. Adding this subcategory expands the practice toolbox and fills in the second slot on the Loose Rhymes subcategory screen.

## Desired Outcome

The Loose Rhymes subcategory screen has a new "Multi-Syllable" button. Tapping it shows a description screen explaining the rhythm/vowel-flow approach with example pairs, then a drill screen identical in layout to Hard Ending Shift Drill: prompt word in a card, "Show Example" / "Next Word" buttons, and a "Home" text button at the bottom.

---

## Tasks

### Phase 1: Data

**Status:** Work Complete

- [x] Create `MultiSyllableData.kt` in the `data/` package
  - Define `data class MultiSyllablePair(val wordA: String, val wordB: String)`
    - Either member can serve as the drill word; the ViewModel randomly assigns seed vs example
  - Populate `val allPairs: List<MultiSyllablePair>` with the starter pairs from the design doc (minus any flagged pairs — see Open Questions)

**Technical Notes:**
Follows the same `object` pattern as `HardEndingShiftData.kt`. Starter pairs:

```
maybe→daydream, taken→paper, open→broken, better→never,
number→thunder, feeling→breathing, matter→after,
quiet→giant, heavy→steady, early→journey, silver→river,
story→glory, music→lucid, echo→mellow
```

---

### Phase 2: ViewModel

**Status:** Work Complete

- [x] Create `MultiSyllableViewModel.kt` in the `viewmodel/` package
  - `seedWord: StateFlow<String>` — the displayed prompt word
  - `exampleWord: StateFlow<String>` — the paired example (hidden until revealed)
  - `isExampleVisible: StateFlow<Boolean>`
  - `nextWord()` — picks a new random pair (avoiding the current), randomly assigns which word is seed vs example, resets `isExampleVisible` to `false`
  - `showExample()` — sets `isExampleVisible` to `true`

**Technical Notes:**
Plain `ViewModel` (not `AndroidViewModel`). Identical structure to `HardEndingShiftViewModel.kt` — swap in `MultiSyllableData` and `MultiSyllablePair`.

---

### Phase 3: Screens

**Status:** Work Complete

- [x] Create `MultiSyllableDescriptionScreen.kt` in `ui/screens/`
  - `TopAppBar` with back navigation, title "Multi-Syllable"
  - Description text: shape/sound matching across syllables; focus on rhythm, vowel flow, stress pattern; avoid perfect rhymes
  - Example pairs list: maybe→daydream, taken→paper, better→never
  - "Start Drill" `Button` navigating to the drill screen
- [x] Create `MultiSyllableDrillScreen.kt` in `ui/screens/`
  - `TopAppBar` with back navigation, title "Multi-Syllable"
  - Prompt word in a `Card` (same style as `HardEndingShiftDrillScreen`)
  - Example text below card, visible only when `isExampleVisible == true`
  - "Show Example" `OutlinedButton` — hidden once example is visible
  - "Next Word" `Button`
  - "Home" `TextButton` at the bottom — pops back stack to Home

**Technical Notes:**
`MultiSyllableDrillScreen` is a direct copy of `HardEndingShiftDrillScreen` with the ViewModel type and title swapped. The "Home" button uses `navController.popBackStack(Screen.Home.route, inclusive = false)`, wired via an `onHome` parameter — same pattern established in DC6.

---

### Phase 4: Navigation

**Status:** Work Complete

- [x] Add routes to `Screen.kt`:
  - `MultiSyllableDescription : Screen("multi_syllable_description")`
  - `MultiSyllableDrill : Screen("multi_syllable_drill")`
- [x] Update `AppNavigation.kt`:
  - Add `composable(Screen.MultiSyllableDescription.route)` → `MultiSyllableDescriptionScreen`
  - Add `composable(Screen.MultiSyllableDrill.route)` → `MultiSyllableDrillScreen` (pass `onHome` and `onBack`)
- [x] Update `LooseRhymesSubcategoryScreen.kt`:
  - Add `onMultiSyllableClick: () -> Unit` parameter
  - Add "Multi-Syllable" `Button` below "Hard Ending Shift"
- [x] Update `AppNavigation.kt`'s `LooseRhymesSubcategoryScreen` call to pass `onMultiSyllableClick`

**Technical Notes:**
No changes to `HomeScreen.kt` or `Screen.LooseRhymes` — the subcategory screen already exists and just needs a new button and parameter. The `All` button remains disabled.

---

---

## Notes and Risks

- No new dependencies required.
- `MultiSyllableData.kt` is intentionally separate from `HardEndingShiftData.kt`; the "All" mode will aggregate them in a future cycle.
- The drill screen must include a "Home" button — this was added to `HardEndingShiftDrillScreen` as a post-DC6 fix and should be in from the start here.

---

## Completion Summary

**Completion Date:** 2026-04-25
**Phases Completed:** All (1–4)
**Work Deferred:** None

**Accomplishments:**
- Created `MultiSyllableData.kt` with `MultiSyllablePair` data class and 14 starter pairs (`simple → signal` excluded — same-first-letter rule, applies in both directions since pairs are bidirectional)
- Created `MultiSyllableViewModel.kt` — identical structure to `HardEndingShiftViewModel`, randomly assigns seed vs example each turn
- Created `MultiSyllableDescriptionScreen.kt` — description, example list, "Start Drill" CTA
- Created `MultiSyllableDrillScreen.kt` — prompt card, "Show Example" / "Next Word", "Home" button included from the start
- Added `MultiSyllableDescription` and `MultiSyllableDrill` routes to `Screen.kt`
- Updated `AppNavigation.kt` with two new composable destinations and `onMultiSyllableClick` wired to subcategory screen
- Updated `LooseRhymesSubcategoryScreen.kt` with `onMultiSyllableClick` parameter and "Multi-Syllable" button

**Metrics:**
- Files created: 4 (`MultiSyllableData.kt`, `MultiSyllableViewModel.kt`, `MultiSyllableDescriptionScreen.kt`, `MultiSyllableDrillScreen.kt`)
- Files modified: 3 (`Screen.kt`, `AppNavigation.kt`, `LooseRhymesSubcategoryScreen.kt`)

**Lessons / Notes:**
Bidirectionality of pairs means the same-first-letter rule applies symmetrically — no need to check direction separately. "Home" button included in the drill screen from the start, avoiding the post-cycle fix needed in DC6.
