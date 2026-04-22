# SongSeedClaude – Finalized Android App Specification

## Overview

**SongSeedClaude** is an Android app for practicing **improvised songs** and **rhyme drills**.

The app focuses on:

* fast idea generation
* minimal friction
* repeatable creative practice

There are two primary features:

* **Improv Song**
* **Rhyme Drill**

---

# Core Modes

## 1. Improv Song

Split into two levels of complexity:

* **Normal Mode** → one idea
* **Difficult Mode** → multiple ideas

### Key Definition

The distinction is strictly:

> **Idea Generation Complexity = number of ideas in the prompt**

* Normal Mode = exactly **one idea**
* Difficult Mode = **two or more ideas combined**

---

## 1A. Improv Song – Normal Mode

### Purpose

Provide simple, flexible prompts for immediate improvisation using **any single category**.

### Rule

* Exactly **one concept per prompt**
* No combinations

### Categories

* themes
* images
* characters
* situations
* emotions
* places
* goals
* obstacles
* twists
* elements

---

### Prompt Structures

Each category maps to a single-concept prompt:

* “Sing about **{theme}**”
* “Sing about **{image}**”
* “Sing as **{character}**”
* “Sing about **{situation}**”
* “Express **{emotion}**”
* “Sing about a **{place}**”
* “Sing about wanting to **{goal}**”
* “Sing about a problem: **{obstacle}**”
* “Sing about something where **{twist}**”
* “Include **{element}**”

---

### Constraint

* Never combine categories
* Always exactly one idea

---

## 1B. Improv Song – Difficult Mode

### Purpose

Provide structured challenges by combining multiple ideas.

### Rule

* Must include **two or more concepts**

### Categories

Same as Normal Mode.

---

### Prompt Types

* Theme + Image
* Character + Situation
* Emotion + Place
* Goal + Obstacle
* Multi-element challenge

---

### Examples

* Sing about regret using a broken window as a metaphor
* You are a taxi driver during a midnight confession
* Express jealousy in a grocery store parking lot
* You want to leave town, but your car won’t start

---

# 2. Rhyme Drill

### Purpose

* Improve rhyme recall speed
* Train quick lyrical response

---

## Difficulty Levels

### Easy Mode

#### Rule

A word qualifies as **Easy** if it meets either condition:

* **One syllable**, OR
* **5 or fewer characters**

#### Examples

* light
* car
* rain
* blue
* time

---

### Normal Mode

#### Rule

* All words **not classified as Easy**
* Includes:

  * multi-syllable words
  * longer words
  * words that may be harder to rhyme

#### Important Note

* Difficult-to-rhyme words are **allowed**
* Perfect rhyming success is **not required**

#### Examples

* window
* tomorrow
* silver
* memory
* telephone

---

## Behavior

* User selects difficulty (Easy or Normal)
* Tap **Next Word** to generate
* Immediate display
* Designed for rapid repetition

---

# Screen Flow

## 1. Home Screen

### Layout

* Title: **SongSeed**
* Main buttons:

  * **Improv Song**
  * **Rhyme Drill**
* Secondary:

  * **Settings**

---

## 2. Improv Mode Selection Screen

### Layout

* Title: **Improv Song**
* Buttons:

  * **Normal Mode**
  * **Difficult Mode**

---

## 3. Improv Prompt Screen

### Layout

* Large centered prompt text
* Button:

  * **Generate**

### Behavior

* Generate immediately on entry
* Each tap replaces the prompt

### Mode Indicator

* “Mode: Normal”
* or “Mode: Difficult”

---

## 4. Rhyme Drill Screen

### Layout

* Large centered word
* Controls:

  * **Next Word**
  * **Difficulty Toggle (Easy / Normal)**

### Behavior

* Instant word generation
* Difficulty affects word pool

---

## 5. Settings Screen

### Options

* Avoid immediate repeats
* Timer duration (future use)
* Enable/disable categories

---

# Generation Logic

## Improv – Normal Mode

1. Select one category
2. Select one item
3. Apply template
4. Display

### Constraint

* Exactly one idea

---

## Improv – Difficult Mode

1. Select template
2. Pull multiple items
3. Combine into prompt

---

## Rhyme Drill

### Easy Mode Filter

```kotlin
fun isEasy(word: Word): Boolean {
    return word.syllableCount == 1 || word.text.length <= 5
}
```

### Normal Mode Filter

```kotlin
fun isNormal(word: Word): Boolean {
    return !isEasy(word)
}
```

### Steps

1. Filter word list based on difficulty
2. Avoid recent repeats (if enabled)
3. Select random word
4. Display

---

# Data Model

## Shared Category Pool (Improv)

* themes
* images
* characters
* situations
* emotions
* places
* goals
* obstacles
* twists
* elements

---

## Rhyme Words

Each word includes:

* text
* syllableCount
* (optional) difficulty tag

---

## Settings (DataStore)

* avoidRecentRepeats: Boolean
* rhymeTimerSeconds: Int
* enabledCategories: Set<String>

---

## In-Memory Session State

```kotlin
data class SessionState(
    val recentPrompts: MutableList<String>,
    val recentWords: MutableList<String>
)
```

---

# Architecture

## Tech Stack

* Kotlin
* Jetpack Compose
* MVVM
* DataStore

## ViewModels

* `HomeViewModel`
* `ImprovViewModel`
* `RhymeViewModel`
* `SettingsViewModel`

---

# UX Principles

## Immediate Interaction

* Enter → prompt appears
* Tap → new prompt

## Simplicity

* Minimal controls
* Clear purpose per screen

## Fast Creative Loop

* tap → sing → tap → sing → repeat

---

# MVP Scope (Version 1)

## Included

* Home screen
* Improv mode selection
* Normal Mode (single idea)
* Difficult Mode (multi-idea)
* Rhyme Drill (Easy + Normal)
* Built-in word lists
* Generate / Next buttons

---

# Product Summary

SongSeed is built around a clear structure:

* **Improv difficulty = number of ideas**
* **Rhyme difficulty = word simplicity vs complexity**

This keeps both systems intuitive:

* Improv: simple → complex ideas
* Rhyme: simple → harder words

The result is a fast, focused tool for consistent improvisation practice.

---
