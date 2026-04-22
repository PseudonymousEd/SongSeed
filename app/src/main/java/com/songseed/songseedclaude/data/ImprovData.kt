package com.songseed.songseedclaude.data

object ImprovData {

    val themes = listOf(
        "love", "loss", "freedom", "home", "time", "change", "hope", "fear",
        "joy", "regret", "betrayal", "longing", "pride", "shame", "revenge",
        "forgiveness", "survival", "identity", "solitude", "connection",
        "memory", "ambition", "redemption", "sacrifice", "loyalty"
    )

    val images = listOf(
        "a broken window", "the open sea", "a burning candle", "an empty chair",
        "a crumbling wall", "the rising sun", "a fading photograph", "a locked gate",
        "falling leaves", "a distant storm", "a crowded room", "a single road",
        "a rusted key", "a silent phone", "a torn letter", "a broken compass",
        "a half-filled glass", "a fire burning low", "an overgrown path", "a bridge in fog"
    )

    val characters = listOf(
        "a wandering stranger", "an old sailor", "a taxi driver at midnight",
        "a lost child", "a returning soldier", "a heartbroken lover",
        "a midnight thief", "a weary traveler", "a forgotten poet",
        "a restless ghost", "a street musician", "an old lighthouse keeper",
        "a runaway bride", "a retired boxer", "a dreaming prisoner"
    )

    val situations = listOf(
        "leaving home for the last time", "waiting at a bus stop in the rain",
        "standing at a crossroads", "saying goodbye at a train station",
        "waking up in an unfamiliar place", "watching the last sunset of summer",
        "finding an old letter", "hearing a song from the past",
        "walking through an empty house", "driving through the night alone",
        "packing up the last box", "staring at a blank page",
        "missing a flight", "running out of time", "getting caught in a lie"
    )

    val emotions = listOf(
        "jealousy", "longing", "relief", "pride", "shame", "euphoria",
        "dread", "nostalgia", "rage", "contentment", "despair", "wonder",
        "confusion", "gratitude", "resentment", "bittersweet joy",
        "quiet sadness", "reckless hope", "hollow victory", "overwhelming awe"
    )

    val places = listOf(
        "a grocery store parking lot", "a highway at midnight", "an empty beach",
        "a rooftop at dawn", "a small town in winter", "a crowded subway",
        "a mountain overlook", "a quiet library", "a neon-lit bar",
        "an overgrown garden", "a hospital waiting room", "an abandoned church",
        "a fire escape in the city", "a foggy harbor", "a motel on the edge of town"
    )

    val goals = listOf(
        "leave town", "find love", "start over", "be heard", "reach the coast",
        "find peace", "go back home", "make things right", "disappear for a while",
        "be understood", "break free", "win someone back", "see the world",
        "forget the past", "build something new"
    )

    val obstacles = listOf(
        "your car won't start", "the door is locked", "a broken promise stands in the way",
        "a missed call changed everything", "a fading memory blocks the path",
        "there's no money left", "fear is holding you back", "you took a wrong turn",
        "the bridge is out", "a storm is rolling in", "the letter came too late",
        "trust was broken", "the road ends here", "you lost the only key",
        "time has run out"
    )

    val twists = listOf(
        "the letter never arrived", "it was all a dream", "the stranger knew your name",
        "the key didn't fit", "there was no signal", "they were already gone",
        "the map was wrong", "you were the last one left", "the door was open all along",
        "it turned out to be a test", "the voice on the phone was familiar",
        "the photo was of you", "the ending was just a beginning",
        "what you feared most saved you", "the person you were waiting for was you"
    )

    val elements = listOf(
        "a clock ticking", "distant thunder", "a single candle", "an old photograph",
        "a closing door", "a song on the radio", "a glass of water nearly empty",
        "a flickering light", "a train whistle in the dark", "smoke rising slowly",
        "a ringing phone", "an open window", "rain on glass",
        "a crowded silence", "a match being struck"
    )

    private val allCategories: List<Pair<String, List<String>>> = listOf(
        "theme" to themes,
        "image" to images,
        "character" to characters,
        "situation" to situations,
        "emotion" to emotions,
        "place" to places,
        "goal" to goals,
        "obstacle" to obstacles,
        "twist" to twists,
        "element" to elements
    )

    private fun buildPrompt(category: String, item: String): String = when (category) {
        "theme" -> "Sing about $item"
        "image" -> "Sing about $item"
        "character" -> "Sing as $item"
        "situation" -> "Sing about $item"
        "emotion" -> "Express $item"
        "place" -> "Sing about $item"
        "goal" -> "Sing about wanting to $item"
        "obstacle" -> "Sing about a problem: $item"
        "twist" -> "Sing about something where $item"
        "element" -> "Include $item in your song"
        else -> "Sing about $item"
    }

    private fun buildDifficultPrompt(): String = when ((0..4).random()) {
        0 -> "Sing about ${themes.random()} using ${images.random()} as a metaphor"
        1 -> "You are ${characters.random()} who is ${situations.random()}"
        2 -> "Express ${emotions.random()} in ${places.random()}"
        3 -> "You want to ${goals.random()}, but ${obstacles.random()}"
        else -> {
            val e1 = elements.random()
            val e2 = elements.filter { it != e1 }.random()
            "Include $e1 and $e2 in a song about ${themes.random()}"
        }
    }

    fun generateNormalPrompt(recentPrompts: List<String>): String {
        repeat(10) {
            val (category, items) = allCategories.random()
            val prompt = buildPrompt(category, items.random())
            if (!recentPrompts.contains(prompt)) return prompt
        }
        val (category, items) = allCategories.random()
        return buildPrompt(category, items.random())
    }

    fun generateDifficultPrompt(recentPrompts: List<String>): String {
        repeat(10) {
            val prompt = buildDifficultPrompt()
            if (!recentPrompts.contains(prompt)) return prompt
        }
        return buildDifficultPrompt()
    }
}
