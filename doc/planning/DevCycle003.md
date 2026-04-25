# DevCycle 003: Word Addition Utility Script

**Status:** Work Complete
**Start Date:** 2026-04-25
**Target Completion:** 2026-04-25
**Focus:** Create a Python utility script that adds new one-syllable words to WordData.kt, skipping duplicates and reporting how many were inserted.

---

## Goal

Manually editing `WordData.kt` to add rhyme words is error-prone and slow, especially when adding batches of words. A small Python utility in the `support/` directory will automate this: given a list of words, it checks which are already present in the file, inserts the new ones into the 1-syllable easy section, and reports the count added. No syllable validation is performed — the caller is responsible for providing 1-syllable words.

## Desired Outcome

A script at `support/add_words.py` that can be run against the current `WordData.kt`, accepts a word list as command-line arguments (`python add_words.py light rain blue`), normalizes each word to lowercase, inserts any missing words in `Word("text", 1)` format into the easy-words section, and prints a single summary line such as `Added 3 word(s).`

---

## Tasks

### Phase 1: Script Creation

**Status:** Work Complete

- [x] Create `support/` directory if it does not exist
- [x] Write `support/add_words.py`:
  - Accept words as command-line arguments (`python add_words.py word1 word2 ...`)
  - Normalize each input word to lowercase
  - Parse existing `Word("...", ...)` entries from `WordData.kt` to build a set of known words
  - For each word, skip if already present
  - Append new `Word("text", 1),` entries at the end of the 1-syllable section (just before the `// Multi-syllable` comment)
  - Print `Added N word(s).` to stdout (does not list which words)
- [x] Verify script on a small test batch before considering complete

**Technical Notes:**
Target file: `app/src/main/java/com/songseed/songseedclaude/data/WordData.kt`

The insertion point is the blank line immediately before the `// Multi-syllable, longer than 5 chars (Normal mode)` comment. The script should locate this marker via a regex or string search rather than hardcoding a line number, so it stays correct as the file grows.

New entries should match the existing format exactly: `        Word("word", 1),` (8 spaces of indent, trailing comma).

Duplicate detection compares the lowercased input word against the existing `text` fields (stripped of quotes, lowercased), so input is normalized before any comparison is made.

---

## Notes and Risks

- The script modifies `WordData.kt` in place; the caller should ensure the file is not open for editing in Android Studio at the same time (Android Studio will detect the external change and prompt to reload).
- No risk to app logic — only additive changes to a data list.
- Script is a developer tool only and is not part of the Android build.

---

## Completion Summary

**Completion Date:** 2026-04-25
**Phases Completed:** All (1)
**Work Deferred:** None

**Accomplishments:**
- Created `support/add_words.py` — accepts words via command-line args, lowercases input, skips duplicates, inserts new `Word("text", 1),` entries before the multi-syllable section marker, and reports count added

**Metrics:**
- Files created: 1 (`support/add_words.py`)
- Files modified: 0

**Lessons / Notes:**
Insertion point is located by searching for the `// Multi-syllable` comment string rather than a hardcoded line number, so the script stays correct as the word list grows.
