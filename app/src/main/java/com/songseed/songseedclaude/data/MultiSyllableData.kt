package com.songseed.songseedclaude.data

data class MultiSyllablePair(val wordA: String, val wordB: String)

object MultiSyllableData {

    val allPairs = listOf(
        MultiSyllablePair("maybe", "daydream"),
        MultiSyllablePair("taken", "paper"),
        MultiSyllablePair("open", "broken"),
        MultiSyllablePair("better", "never"),
        MultiSyllablePair("number", "thunder"),
        MultiSyllablePair("feeling", "breathing"),
        MultiSyllablePair("matter", "after"),
        MultiSyllablePair("quiet", "giant"),
        MultiSyllablePair("heavy", "steady"),
        MultiSyllablePair("early", "journey"),
        MultiSyllablePair("silver", "river"),
        MultiSyllablePair("story", "glory"),
        MultiSyllablePair("music", "lucid"),
        MultiSyllablePair("echo", "mellow"),
    )
}
