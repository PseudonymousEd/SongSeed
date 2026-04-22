# DevCycle 001: Initial App Scaffold

**Status:** Work Complete
**Start Date:** 2026-04-22
**Target Completion:** 2026-04-22
**Focus:** Convert the initial spec into a complete Android project scaffold that compiles and covers all MVP features.

---

## Goal

The initial plan document (`doc/Ideas/initialplan.md`) fully defined the MVP scope for SongSeed: two main features (Improv Song and Rhyme Drill), a settings screen, and a clear architecture (Kotlin, Jetpack Compose, MVVM, DataStore). This DevCycle translates that spec into a real Android project — all source files, build configuration, navigation, data, ViewModels, and screens — ready to open in Android Studio and build.

## Desired Outcome

A complete Android project structure exists under the repo root. All five screens are implemented. Improv prompt generation and rhyme word selection logic match the spec exactly. The project can be opened in Android Studio and synced without errors.

---

## Tasks

### Phase 1: Build System and Project Structure

**Status:** Work Complete

- [x] Create `settings.gradle.kts` with repository configuration
- [x] Create root `build.gradle.kts` with plugin declarations
- [x] Create `gradle/libs.versions.toml` version catalog (AGP 8.5.2, Kotlin 2.0.21, Compose BOM 2024.09.03, Navigation, DataStore)
- [x] Create `gradle/wrapper/gradle-wrapper.properties` targeting Gradle 8.7
- [x] Create `gradle.properties` with AndroidX and Kotlin settings
- [x] Create `app/build.gradle.kts` with all dependencies
- [x] Create `app/proguard-rules.pro`
- [x] Create `AndroidManifest.xml`
- [x] Create `res/values/strings.xml` and `res/values/themes.xml`

**Technical Notes:**
Used the Kotlin DSL + version catalog pattern throughout. The `themes.xml` provides a minimal `android:Theme.Material.Light.NoActionBar` base so the manifest has a valid style reference; all real theming is handled in Compose. The `gradlew` wrapper scripts are not included — Android Studio generates them automatically on first sync, or they can be created via `gradle wrapper`.

---

### Phase 2: Data Layer

**Status:** Work Complete

- [x] Create `data/ImprovData.kt` — all ten category lists (themes, images, characters, situations, emotions, places, goals, obstacles, twists, elements) plus Normal and Difficult prompt generators
- [x] Create `data/WordData.kt` — `Word` data class with `text` and `syllableCount`; full easy and normal word pools; `isEasy()` filter matching the spec exactly
- [x] Create `data/SettingsDataStore.kt` — DataStore preferences repository with `avoidRecentRepeats` setting
- [x] Create `data/SessionState.kt` — in-memory recent prompts and words lists (capped at 10)

**Technical Notes:**
`ImprovData` generators use an iteration-based repeat-avoidance approach (up to 10 tries before falling back) rather than recursion, to avoid any risk of stack overflow with a tight recent-prompts list. The `isEasy` filter is implemented verbatim from the spec: `syllableCount == 1 || text.length <= 5`. The `SettingsDataStore.kt` exposes a `Context.dataStore` extension property so all ViewModels can share the same DataStore instance.

---

### Phase 3: ViewModels

**Status:** Work Complete

- [x] Create `viewmodel/ImprovViewModel.kt` — generates Normal and Difficult prompts, reads `avoidRecentRepeats` from DataStore, tracks session state
- [x] Create `viewmodel/RhymeViewModel.kt` — generates next rhyme word, manages Easy/Normal toggle, tracks session state
- [x] Create `viewmodel/SettingsViewModel.kt` — exposes `avoidRecentRepeats` as `StateFlow`, writes changes back to DataStore

**Technical Notes:**
All three ViewModels extend `AndroidViewModel` to get `Application` context for DataStore access. `ImprovViewModel` and `RhymeViewModel` each instantiate their own `SettingsRepository` — this is fine because DataStore is a singleton backed by the same file. `StateFlow` is used throughout for UI state; settings are read with `first()` at generate-time rather than collected continuously, which avoids over-collecting in a fire-and-forget model.

---

### Phase 4: UI Theme and Screens

**Status:** Work Complete

- [x] Create `ui/theme/Color.kt` — dark palette: `BackgroundDark #0D0D0D`, `AccentGold #D4A017`, `AccentAmber #E8821C`
- [x] Create `ui/theme/Theme.kt` — `SongSeedTheme` using `darkColorScheme` with gold/amber primary
- [x] Create `ui/theme/Type.kt` — typography scale
- [x] Create `ui/screens/HomeScreen.kt` — title, Improv Song button, Rhyme Drill button, Settings text button
- [x] Create `ui/screens/ImprovModeSelectionScreen.kt` — Normal Mode and Difficult Mode buttons
- [x] Create `ui/screens/ImprovPromptScreen.kt` — large prompt card, Generate button, auto-generates on entry via `LaunchedEffect`
- [x] Create `ui/screens/RhymeDrillScreen.kt` — large word card, Easy/Normal toggle switch, Next Word button, auto-generates on entry
- [x] Create `ui/screens/SettingsScreen.kt` — Avoid immediate repeats toggle with description

**Technical Notes:**
All screens that need back navigation use `Scaffold` + `TopAppBar` with `Icons.AutoMirrored.Filled.ArrowBack`. `HomeScreen` has no back button and is layout-only (no Scaffold needed). `ImprovPromptScreen` and `RhymeDrillScreen` both fire an initial generation via `LaunchedEffect` so the user sees content immediately on entry, matching the spec's "Generate immediately on entry" requirement.

---

### Phase 5: Navigation and Entry Point

**Status:** Work Complete

- [x] Create `navigation/Screen.kt` — sealed class with typed routes
- [x] Create `navigation/AppNavigation.kt` — `NavHost` with all five destinations; `mode` string argument on `ImprovPrompt` route declared via `navArgument`
- [x] Create `MainActivity.kt` — `enableEdgeToEdge()`, `SongSeedTheme`, `rememberNavController`, `AppNavigation`

**Technical Notes:**
The `mode` parameter on the Improv Prompt screen is passed as a path segment (`improv_prompt/{mode}`) with an explicit `navArgument` declaration so Navigation correctly parses it from the back stack entry. `enableEdgeToEdge()` is called before `setContent` so the system bars are transparent and the Compose layout fills the full screen.

---

## Open Questions

1. **Launcher icon resources**
   The manifest references `@mipmap/ic_launcher` and `@mipmap/ic_launcher_round`, which are not included in this scaffold. Android Studio generates default launcher icons automatically when creating a new project. These will need to be added before a release build.
   Recommendation: Add mipmap resources in a future DevCycle or use Android Studio's Image Asset tool.

2. **Gradle wrapper scripts (`gradlew` / `gradlew.bat`)**
   The shell/batch wrapper scripts are not included because they are binary-ish and normally generated by tooling. Android Studio generates them on first sync.
   Recommendation: Run `gradle wrapper` in the project root, or let Android Studio handle it on first open.

---

## Notes and Risks

- The project has not been built or run on a device yet. Compilation correctness is based on code review, not a build run.
- Word syllable counts in `WordData.kt` are manually assigned — a small number could be wrong. This only affects Easy/Normal classification; incorrect counts would at most put a word in the wrong pool, not cause a crash.
- The `SettingsRepository` is instantiated per-ViewModel rather than injected. This is acceptable for MVP scope but should be refactored to a single shared instance (via a simple Application-level singleton or a DI library) if the settings surface grows.

---

## Completion Summary

**Completion Date:** 2026-04-22
**Phases Completed:** All (1–5)
**Work Deferred:** Launcher icons, gradlew scripts, device testing

**Accomplishments:**
- Created complete Android project scaffold from a spec document in a single session
- Implemented all five screens matching the spec's Screen Flow section
- Implemented Improv Normal and Difficult prompt generation with all ten category lists
- Implemented Rhyme Drill with Easy/Normal word filter matching the spec's exact rule
- Implemented repeat-avoidance logic backed by DataStore persistence
- Applied a dark gold/amber theme appropriate for a music practice app

**Metrics:**
- Files created: 28
- Kotlin source files: 18
- Gradle/config files: 7
- Resource/manifest files: 3

**Lessons / Notes:**
Working directly from a well-structured spec document made the scaffold straightforward — every design decision (category names, prompt templates, difficulty rules, screen flow) was already resolved. The main judgment calls were around implementation details the spec left open: iteration vs. recursion for repeat-avoidance, AndroidViewModel for DataStore context, and the visual theme direction.
