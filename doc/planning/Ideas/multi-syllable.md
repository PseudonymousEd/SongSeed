# 🎤 SongSeed – Multi-Syllable Loose Rhymes (Design Overview)

## 1. Category

**Loose Rhymes → Multi-Syllable**

> Match the rhythm and vowel flow of a word across multiple syllables, without using perfect rhymes.

---

## 2. Description Screen

**Title:**  
Multi-Syllable Loose Rhymes

**Description:**
> These rhymes match the **shape and sound** of a word across multiple syllables.
>
> Focus on:
> - similar rhythm
> - similar vowel flow
> - similar stress pattern
>
> Avoid perfect rhymes. Aim for something that **feels similar when spoken or sung**.

**Example pairs:**
- maybe → daydream
- taken → paper
- better → never

**CTA Button:**  
👉 **Start Drill**

---

## 3. Drill Screen (Core Interaction)

### Layout

**Main Word (center):**  
`maybe`

---

### Buttons

- **Show Example**
- **Next Word**

---

## 4. Behavior

### Show Example
- Reveals **one example only**
- Each prompt has a single predefined pair

Example flow:

maybe  
→ *(tap)* daydream

---

### Next Word
- Advances to a new prompt word
- Resets example state

---

## 5. Design Principles

- No correctness checking
- No required number of responses
- User participates mentally or vocally
- Examples define the quality bar
- Focus on **sound and rhythm**, not exact matches

---

## 6. Starter Dataset

Each prompt maps to exactly **one example**, bidirectionally.

---

maybe → daydream  
taken → paper  
open → broken  
better → never
number → thunder  
feeling → breathing
matter → after  
quiet → giant  
heavy → steady  
early → journey
silver → river  
story → glory  
music → lucid  
echo → mellow

---

## 🚀 Next Step

- Refine pairs based on how they sound when spoken or sung
- Expand dataset with additional multi-syllable patterns
- Integrate with line-based or phrase-based drills