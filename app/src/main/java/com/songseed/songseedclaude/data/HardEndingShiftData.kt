package com.songseed.songseedclaude.data

data class HardEndingShiftPair(val wordA: String, val wordB: String)

object HardEndingShiftData {

    val allPairs = listOf(
        HardEndingShiftPair("bad", "cat"),
        HardEndingShiftPair("cat", "map"),
        HardEndingShiftPair("bed", "set"),
        HardEndingShiftPair("dog", "top"),
        HardEndingShiftPair("sun", "cup"),
        HardEndingShiftPair("book", "put"),
        HardEndingShiftPair("stone", "note"),
        HardEndingShiftPair("grass", "cap"),
        HardEndingShiftPair("rain", "late"),
        HardEndingShiftPair("plate", "make"),
        HardEndingShiftPair("road", "note"),
        HardEndingShiftPair("made", "take"),
        HardEndingShiftPair("side", "time"),
        HardEndingShiftPair("red", "bet"),
        HardEndingShiftPair("mud", "cut"),
    )
}
