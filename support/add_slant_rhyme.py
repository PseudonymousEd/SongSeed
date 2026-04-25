import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
SLANT_RHYME_PATH = REPO_ROOT / "app/src/main/java/com/songseed/songseedclaude/data/SlantRhymeData.kt"


def main():
    if len(sys.argv) != 3:
        print("Usage: python add_slant_rhyme.py word1 word2")
        sys.exit(1)

    word1, word2 = sys.argv[1].lower(), sys.argv[2].lower()

    with open(SLANT_RHYME_PATH, "r", encoding="utf-8") as f:
        content = f.read()

    existing_pairs = re.findall(r'SlantRhymePair\("([^"]+)",\s*"([^"]+)"\)', content)
    existing_sets = {frozenset(pair) for pair in existing_pairs}

    if frozenset({word1, word2}) in existing_sets:
        print("Pair already exists.")
        return

    new_entry = f'        SlantRhymePair("{word1}", "{word2}"),'

    # Insert after the last SlantRhymePair line (trailing comma optional)
    last_match = None
    for m in re.finditer(r'SlantRhymePair\([^)]+\),?', content):
        last_match = m

    if last_match is None:
        print("Error: could not find existing SlantRhymePair entries in SlantRhymeData.kt")
        sys.exit(1)

    insert_pos = last_match.end()
    # If the last entry has no trailing comma, add one before inserting
    separator = "\n" if content[insert_pos - 1] == "," else ",\n"
    updated = content[:insert_pos] + separator + new_entry + content[insert_pos:]

    with open(SLANT_RHYME_PATH, "w", encoding="utf-8") as f:
        f.write(updated)

    print("Added 1 pair.")


if __name__ == "__main__":
    main()
