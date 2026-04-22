# SongSeed

An Android app for practising improvised songs and rhyme drills.

Built for fast idea generation with minimal friction — tap, sing, repeat.

---

## Features

### Improv Song

Generates a prompt for immediate improvisation.

- **Normal Mode** — one idea drawn from a single category (theme, image, character, emotion, etc.)
- **Difficult Mode** — two or more ideas combined (e.g. character + situation, emotion + place)

### Rhyme Drill

Displays a word to rhyme against.

- **Easy** — one syllable or five characters or fewer
- **Normal** — multi-syllable, longer words that are harder to rhyme

Both modes support repeat avoidance, skipping recently shown prompts and words.

---

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM (ViewModel + StateFlow)
- Jetpack Navigation
- DataStore (settings persistence)

---

## Project Structure

```
app/src/main/java/com/songseed/songseedclaude/
├── MainActivity.kt
├── navigation/
│   ├── Screen.kt              # Typed route definitions
│   └── AppNavigation.kt       # NavHost
├── data/
│   ├── ImprovData.kt          # Category lists and prompt generators
│   ├── WordData.kt            # Rhyme word list and Easy/Normal filter
│   ├── SettingsDataStore.kt   # DataStore preferences repository
│   └── SessionState.kt        # In-memory recent history
├── viewmodel/
│   ├── ImprovViewModel.kt
│   ├── RhymeViewModel.kt
│   └── SettingsViewModel.kt
└── ui/
    ├── theme/                 # Color, typography, Material3 theme
    └── screens/               # One file per screen
```

---

## Building

Open the project root in Android Studio. Android Studio will sync Gradle and generate any missing wrapper files automatically.

Minimum SDK: 24  
Target SDK: 35

---

## About This Project

This project was written using [Claude](https://claude.ai) (Anthropic). The same specification was used to generate a parallel version of the app using Codex (OpenAI). The Claude-generated version was selected as the one to share publicly.

---

## License

MIT License — see [LICENSE](LICENSE).
