package com.songseed.songseedclaude.data

data class Word(val text: String, val syllableCount: Int)

object WordData {

    val allWords = listOf(
        // 1-syllable words (always Easy)
        Word("light", 1), Word("rain", 1), Word("blue", 1), Word("time", 1),
        Word("day", 1), Word("night", 1), Word("song", 1), Word("heart", 1),
        Word("face", 1), Word("hand", 1), Word("star", 1), Word("door", 1),
        Word("tree", 1), Word("fire", 1), Word("road", 1), Word("wave", 1),
        Word("moon", 1), Word("sun", 1), Word("sky", 1), Word("wind", 1),
        Word("fall", 1), Word("love", 1), Word("dream", 1), Word("high", 1),
        Word("cry", 1), Word("rise", 1), Word("shine", 1), Word("fine", 1),
        Word("mine", 1), Word("line", 1), Word("stay", 1), Word("play", 1),
        Word("pray", 1), Word("way", 1), Word("bright", 1), Word("might", 1),
        Word("fight", 1), Word("right", 1), Word("sight", 1), Word("white", 1),
        Word("game", 1), Word("flame", 1), Word("name", 1), Word("came", 1),
        Word("pain", 1), Word("gain", 1), Word("chain", 1), Word("train", 1),
        Word("break", 1), Word("shake", 1), Word("wake", 1), Word("make", 1),
        Word("take", 1), Word("fly", 1), Word("die", 1), Word("try", 1),
        Word("lie", 1), Word("buy", 1), Word("ride", 1), Word("hide", 1),
        Word("wide", 1), Word("side", 1), Word("pride", 1), Word("stone", 1),
        Word("bone", 1), Word("lone", 1), Word("moan", 1), Word("phone", 1),
        Word("tone", 1), Word("zone", 1), Word("call", 1), Word("hall", 1),
        Word("tall", 1), Word("wall", 1), Word("ball", 1), Word("cold", 1),
        Word("bold", 1), Word("hold", 1), Word("gold", 1), Word("told", 1),
        Word("old", 1), Word("ground", 1), Word("sound", 1), Word("found", 1),
        Word("round", 1), Word("bound", 1), Word("word", 1), Word("heard", 1),
        Word("burn", 1), Word("turn", 1), Word("learn", 1), Word("yearn", 1),
        Word("gone", 1), Word("on", 1), Word("run", 1), Word("done", 1),
        Word("one", 1), Word("sun", 1), Word("fun", 1), Word("gun", 1),
        Word("sing", 1), Word("ring", 1), Word("bring", 1), Word("thing", 1),
        Word("king", 1), Word("spring", 1), Word("sting", 1), Word("swing", 1),
        Word("back", 1), Word("track", 1), Word("crack", 1), Word("black", 1),
        Word("red", 1), Word("bed", 1), Word("head", 1), Word("dead", 1),
        Word("bread", 1), Word("led", 1), Word("said", 1), Word("thread", 1),

        // Multi-syllable, longer than 5 chars (Normal mode)
        Word("window", 2), Word("silver", 2), Word("stranger", 2),
        Word("whisper", 2), Word("midnight", 2), Word("thunder", 2),
        Word("nowhere", 2), Word("answer", 2), Word("summer", 2),
        Word("winter", 2), Word("picture", 2), Word("heaven", 2),
        Word("country", 2), Word("broken", 2), Word("driven", 2),
        Word("written", 2), Word("spoken", 2), Word("frozen", 2),
        Word("chosen", 2), Word("stolen", 2), Word("golden", 2),
        Word("fallen", 2), Word("morning", 2), Word("evening", 2),
        Word("burning", 2), Word("turning", 2), Word("learning", 2),
        Word("searching", 2), Word("running", 2), Word("coming", 2),
        Word("sparrow", 2), Word("shadow", 2), Word("sorrow", 2),
        Word("hollow", 2), Word("narrow", 2), Word("borrow", 2),
        Word("nothing", 2), Word("something", 2), Word("somewhere", 2),
        Word("slowly", 2), Word("quietly", 3), Word("finally", 3),
        Word("suddenly", 3), Word("together", 3), Word("forever", 3),
        Word("remember", 3), Word("whenever", 3), Word("whatever", 3),
        Word("wherever", 3), Word("however", 3), Word("telephone", 3),
        Word("tomorrow", 3), Word("memory", 3), Word("energy", 3),
        Word("beautiful", 3), Word("ordinary", 4), Word("understand", 3),
        Word("yesterday", 3), Word("everything", 3), Word("anything", 3),
        Word("everyone", 3), Word("anyone", 3), Word("someone", 2),
        Word("beginning", 3), Word("returning", 3), Word("believing", 3),
        Word("remember", 3), Word("surrender", 3), Word("together", 3),
        Word("discover", 3), Word("forgiven", 3), Word("forbidden", 3),
        Word("horizon", 3), Word("forgotten", 3), Word("longing", 2),
        Word("wandering", 3), Word("wondering", 3), Word("listening", 3),
        Word("whispering", 3), Word("shimmering", 3), Word("glimmering", 3)
    )

    fun isEasy(word: Word): Boolean = word.syllableCount == 1 || word.text.length <= 5

    fun getEasyWords(): List<Word> = allWords.filter { isEasy(it) }

    fun getNormalWords(): List<Word> = allWords.filter { !isEasy(it) }
}
