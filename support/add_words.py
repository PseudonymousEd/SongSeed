import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
WORDDATA_PATH = REPO_ROOT / "app/src/main/java/com/songseed/songseedclaude/data/WordData.kt"
INSERTION_MARKER = "// Multi-syllable"


def main():
    if len(sys.argv) < 2:
        print("Usage: python add_words.py word1 word2 ...")
        sys.exit(1)

    input_words = [w.lower() for w in sys.argv[1:]]

    with open(WORDDATA_PATH, "r", encoding="utf-8") as f:
        content = f.read()

    existing = set(re.findall(r'Word\("([^"]+)"', content))

    to_add = [w for w in input_words if w not in existing]

    if not to_add:
        print("Added 0 word(s).")
        return

    new_lines = "\n".join(f'        Word("{w}", 1),' for w in to_add) + "\n"

    insert_pos = content.find(INSERTION_MARKER)
    if insert_pos == -1:
        print("Error: could not find insertion marker in WordData.kt")
        sys.exit(1)

    # Insert before the marker line, after the preceding newline
    line_start = content.rfind("\n", 0, insert_pos) + 1
    updated = content[:line_start] + new_lines + content[line_start:]

    with open(WORDDATA_PATH, "w", encoding="utf-8") as f:
        f.write(updated)

    print(f"Added {len(to_add)} word(s).")


if __name__ == "__main__":
    main()
