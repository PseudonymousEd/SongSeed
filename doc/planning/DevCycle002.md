# DevCycle 002: Data Expansion and Improv Mode Toggle

**Status:** Work Complete
**Start Date:** 2026-04-23
**Target Completion:** 2026-04-23
**Focus:** Expand prompt and word content from reference files, and unify Improv Song mode selection into an inline toggle matching Rhyme Drill's UX pattern.

---

## Goal

Two goals drive this cycle. First, the reference files in `doc/reference/` (`ImprovRepository.kt`, `RhymeRepository.kt`) represent a richer version of the app's data and generation logic. Incorporating their ideas means expanding each ImprovData category list and the WordData pool, and tightening the difficult prompt generation to use the more specific named combination patterns shown in the reference. Second, there is an inconsistency between Improv Song and Rhyme Drill: Rhyme Drill lets you toggle Easy/Normal in-place on the drill screen, while Improv Song requires a separate mode selection screen before you can generate prompts. Replacing the selection screen with an inline Normal/Difficult toggle unifies the two features and reduces the navigation depth by one step.

## Desired Outcome

All ImprovData category lists are longer and more varied. The WordData easy and normal word pools are expanded. Improv Song's difficult prompt generation more precisely handles named category pair combinations (Goals+Obstacles, Characters+Situations, Emotions+Places, Themes+Images). The `ImprovModeSelectionScreen` is removed; navigating to Improv Song from the home screen goes directly to a single `ImprovPromptScreen` that has a Normal/Difficult toggle switch styled identically to Rhyme Drill's Easy/Normal toggle.

---

## Tasks

### Phase 1: Data Expansion

**Status:** Work Complete

- [x] Expand `ImprovData.kt` — add 10–15 new entries to each of the ten category lists (themes, images, characters, situations, emotions, places, goals, obstacles, twists, elements)
- [x] Rewrite `ImprovData.buildDifficultPrompt()` to use named category-pair combination logic matching `ImprovRepository.kt` reference: Goals+Obstacles, Characters+Situations, Emotions+Places, Themes+Images as preferred pairs; generic fallback for other combinations
- [x] Expand `WordData.kt` — add more 1-syllable easy words and more multi-syllable normal words to the `allWords` list

**Technical Notes:**
The reference `ImprovRepository.kt` shows the difficult prompt logic organized around specific named pairings with natural-language templates:
- Goals + Obstacles → `"You want to {goal}, but {obstacle}"`
- Characters + Situations → `"You are {character} {situation}"`
- Emotions + Places → `"Express {emotion} in {place}"`
- Themes + Images → `"Sing about {theme} using {image} as a metaphor"`
- Fallback → `"Create a song that includes {fragments}"`

The current `buildDifficultPrompt()` in `ImprovData.kt` already covers these cases in a `when ((0..4).random())` switch but uses randomly sampled items rather than selecting from a unified `targetCount` pool. Aligning it to the reference pattern makes the combinations feel more intentional. No change to the public API (`generateDifficultPrompt`) is needed — only the private builder.

The reference `RhymeRepository.kt` confirms the `isEasy` rule (`syllableCount == 1 || text.length <= 5`) matches what `WordData.kt` already uses, so no logic changes are needed there — only additional word entries.

---

### Phase 2: Improv Mode Toggle

**Status:** Work Complete

- [x] Add `isNormalMode: StateFlow<Boolean>` (default `true`) and `toggleMode()` to `ImprovViewModel.kt`
- [x] Refactor `ImprovViewModel.generatePrompt()` to read mode from its own state rather than accept a `mode: String` parameter
- [x] Rewrite `ImprovPromptScreen.kt` — remove the `mode: String` parameter, add a Normal/Difficult toggle Row identical in structure to `RhymeDrillScreen`'s Easy/Normal toggle, wire toggle to `viewModel.toggleMode()`, regenerate prompt on toggle
- [x] Update `Screen.kt` — remove `ImprovModeSelection` route; update `ImprovPrompt` route to have no `{mode}` path argument
- [x] Update `AppNavigation.kt` — remove the `ImprovModeSelection` composable destination; wire Home's Improv button directly to `Screen.ImprovPrompt.route`; remove `navArgument` declaration from ImprovPrompt composable; stop passing `mode` to `ImprovPromptScreen`
- [x] Delete `ImprovModeSelectionScreen.kt`

**Technical Notes:**
`RhymeDrillScreen.kt` is the direct model for the toggle UI. It uses a `Row` with `Text("Easy")`, a `Switch` (checked = `!isEasyMode`), and `Text("Normal")`, with the active label colored `MaterialTheme.colorScheme.primary` and the inactive label colored `onSurfaceVariant`. The Improv toggle will follow the same pattern: `Text("Normal")`, `Switch` (checked = `!isNormalMode`), `Text("Difficult")`.

The `LaunchedEffect` in `ImprovPromptScreen` currently fires on `mode`. After this change it should fire on `Unit` (same as `RhymeDrillScreen`) since mode is now internal state. A second `LaunchedEffect` keyed on `isNormalMode` should trigger `viewModel.generatePrompt()` when the toggle changes, so the user immediately sees a new prompt for the selected mode — matching the behaviour expected when toggling in Rhyme Drill.

`ImprovViewModel.generatePrompt()` signature change: remove the `mode: String` parameter. All internal mode routing reads from `_isNormalMode.value`. This is a breaking change to the call sites in `ImprovPromptScreen` but those are the only callers; after the screen refactor there are no remaining references to the string-based mode.

Navigation simplification: the `ImprovPrompt` route changes from `improv_prompt/{mode}` to `improv_prompt` (no argument). `Screen.ImprovPrompt` in `Screen.kt` no longer needs a `createRoute(mode)` helper. The `ImprovModeSelection` sealed class entry and its route are removed entirely.

---

## Open Questions

1. **Auto-generate on toggle**
   When the user flips the Normal/Difficult toggle, should a new prompt generate automatically, or should they tap Generate manually?
   Recommendation: Auto-generate, matching the expectation set by Rhyme Drill (toggling Easy/Normal immediately shows a new word). Use a `LaunchedEffect(isNormalMode)` block in `ImprovPromptScreen` that calls `viewModel.generatePrompt()` whenever the mode changes.

---

## Notes and Risks

- Deleting `ImprovModeSelectionScreen.kt` and the `ImprovModeSelection` navigation route is irreversible within a session but trivially recoverable from git. Both changes are contained to files touched in this cycle.
- The `mode: String` parameter removal from `ImprovViewModel.generatePrompt()` is an internal API change. The only caller is `ImprovPromptScreen`, which is being rewritten in the same phase.
- Expanding data lists is additive and carries no risk of breaking existing behaviour.

---

## Completion Summary

**Completion Date:** 2026-04-23
**Phases Completed:** All (1–2)
**Work Deferred:** None

**Accomplishments:**
- Expanded all ten ImprovData category lists with 10–15 new entries each, roughly doubling variety
- Aligned `buildDifficultPrompt()` with the reference: Characters+Situations now uses direct concatenation ("You are {character} {situation}") matching the reference pattern
- Expanded `WordData.allWords` with ~80 new entries across easy (1-syllable) and normal (multi-syllable) pools
- Removed `ImprovModeSelectionScreen` and the intermediate navigation step; Improv Song now goes directly from Home to the prompt screen
- Added Normal/Difficult toggle to `ImprovPromptScreen` styled identically to `RhymeDrillScreen`'s Easy/Normal toggle
- `ImprovViewModel.generatePrompt()` no longer takes a `mode` parameter — mode is owned by ViewModel state
- `LaunchedEffect(isNormalMode)` auto-generates a new prompt on both initial entry and toggle, matching Rhyme Drill behavior

**Metrics:**
- Files modified: 6 (`ImprovData.kt`, `WordData.kt`, `ImprovViewModel.kt`, `ImprovPromptScreen.kt`, `Screen.kt`, `AppNavigation.kt`)
- Files deleted: 1 (`ImprovModeSelectionScreen.kt`)
- New items added to ImprovData: ~95 entries across all ten categories
- New words added to WordData: ~80 entries

**Lessons / Notes:**
Using `LaunchedEffect(isNormalMode)` as the sole effect key in `ImprovPromptScreen` handles both initial entry and toggle changes cleanly — no need for a separate `LaunchedEffect(Unit)`, since the initial value of `isNormalMode` triggers the effect on first composition.
