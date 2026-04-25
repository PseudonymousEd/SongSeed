# DevCycle 005: Slant Rhyme Addition Utility Script

**Status:** Work Complete
**Start Date:** 2026-04-25
**Target Completion:** 2026-04-25
**Focus:** Create a Python utility script that adds a new slant rhyme pair to SlantRhymeData.kt from the command line.

---

## Goal

Adding slant rhyme pairs to `SlantRhymeData.kt` by hand is repetitive and error-prone. A companion script to `add_words.py` will automate this: given two words on the command line, it checks whether that pair already exists (in either order), and if not, appends it to the `allPairs` list. Output is a single summary line.

## Desired Outcome

A script at `support/add_slant_rhyme.py` that accepts exactly two words as command-line arguments (`python add_slant_rhyme.py crate braid`), lowercases both, checks for a duplicate in either word order, and either inserts a new `SlantRhymePair("word1", "word2"),` entry or reports that the pair already exists. Prints `Added 1 pair.` on success or `Pair already exists.` if a duplicate is found.

---

## Tasks

### Phase 1: Script Creation

**Status:** Work Complete

- [x] Write `support/add_slant_rhyme.py`:
  - Require exactly 2 command-line arguments; print usage and exit if not provided
  - Normalize both words to lowercase
  - Parse existing `SlantRhymePair("...", "...")` entries from `SlantRhymeData.kt` to build a set of known pairs
  - Check for the pair in both orders — `(wordA, wordB)` and `(wordB, wordA)` — and skip if either matches
  - Append `        SlantRhymePair("word1", "word2"),` before the closing `    )` of the `allPairs` list
  - Print `Added 1 pair.` or `Pair already exists.`
- [x] Verify script on a test pair before considering complete

**Technical Notes:**
Target file: `app/src/main/java/com/songseed/songseedclaude/data/SlantRhymeData.kt`

Path resolution should use `Path(__file__).resolve().parent.parent` (same pattern as `add_words.py`) so the script works from any working directory.

The insertion point is the line containing `    )` that closes the `allPairs` list. This can be located by searching for the last occurrence of `    )` before the closing `}` of the object, or more reliably by finding the last `SlantRhymePair(` line and inserting after it.

New entries should match the existing format exactly: `        SlantRhymePair("word1", "word2"),` (8 spaces of indent, trailing comma).

Duplicate detection builds a set of frozensets from the existing pairs so that `{crate, braid}` matches both `SlantRhymePair("crate", "braid")` and `SlantRhymePair("braid", "crate")`.

---

## Notes and Risks

- The script modifies `SlantRhymeData.kt` in place; same Android Studio reload caveat as `add_words.py`.
- No risk to app logic — only additive changes to a data list.
- Script is a developer tool only and is not part of the Android build.

---

## Completion Summary

**Completion Date:** 2026-04-25
**Phases Completed:** All (1)
**Work Deferred:** None

**Accomplishments:**
- Created `support/add_slant_rhyme.py` — accepts two words via command-line args, lowercases both, checks for the pair in either order using frozensets, appends a new `SlantRhymePair` entry, and prints `Added 1 pair.` or `Pair already exists.`

**Metrics:**
- Files created: 1 (`support/add_slant_rhyme.py`)
- Files modified: 0

**Lessons / Notes:**
The last entry in the Kotlin list has no trailing comma, so the script must detect whether the matched entry ends with a comma and add one if not before inserting the new line. The regex pattern uses `,?` to match both cases, and the separator logic handles the comma insertion.
