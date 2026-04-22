---
name: SongSeed Android App
description: Android app for improvised song practice and rhyme drills — built with Kotlin + Jetpack Compose + MVVM + DataStore
type: project
---

SongSeed is a Kotlin/Compose Android app with two main features: Improv Song and Rhyme Drill.

**Why:** Practice tool for improvised singing — fast idea generation, minimal friction.

**How to apply:** When working on this project, features are self-contained screens with their own ViewModels. The spec is in doc/Ideas/initialplan.md.

Architecture:
- Package: com.songseed.songseedclaude
- minSdk 24, targetSdk 35, AGP 8.5.2, Kotlin 2.0.21
- Navigation: Compose NavHost with typed route arguments
- Data: ImprovData.kt (prompt generation), WordData.kt (rhyme words), SettingsDataStore.kt (DataStore prefs)
- ViewModels: ImprovViewModel, RhymeViewModel, SettingsViewModel (all AndroidViewModel for Application context)
- Theme: dark gold/amber — BackgroundDark #0D0D0D, AccentGold #D4A017

Key logic:
- Easy rhyme words: syllableCount == 1 OR text.length <= 5
- Normal rhyme words: not easy (multi-syllable AND >5 chars)
- Improv normal: one category/item → prompt template
- Improv difficult: theme+image, character+situation, emotion+place, goal+obstacle, multi-element
- Repeat avoidance: tracked in SessionState (last 10), controlled by DataStore setting
