# AI-Assisted Development (A-AiAssisted)

This document describes how AI tools were used to enhance the Groot chatbot project.

## Tool Used

**Cursor** - An AI-powered code editor that provides inline suggestions, chat-based assistance, and autonomous agent capabilities.

## How AI Assisted This Project

### C-Tagging Implementation

AI (Cursor) assisted with:

1. **Codebase exploration** - Quickly understanding the project structure, data model (Task, ToDo, Deadline, Event), command flow, and storage format without manual file-by-file reading.

2. **Tagging feature design** - Designing the tag storage format for backward compatibility with existing data files. Tags are stored as a comma-separated list appended to each task line (e.g., `T | 0 | Buy milk | fun,urgent`).

3. **Implementation consistency** - Ensuring new `TagCommand` and `UntagCommand` follow the same patterns as existing commands (AddCommand, MarkAsDoneCommand, etc.), and that `FindCommand` correctly handles both keyword search and `#tag` search.

4. **Edge cases** - Handling empty tags, case normalization (tags stored lowercase), and backward compatibility when loading tasks without tags from older data files.

### User Guide (A-UserGuide)

AI assisted with:

1. **Structure and formatting** - Following GitHub Flavored Markdown conventions for the User Guide, including command format documentation with UPPER_CASE parameters and optional/multiple parameter notation.

2. **Completeness** - Ensuring all features (list, add, mark, unmark, delete, find, tag, untag) are documented with clear examples.

## Limitations

AI suggestions were reviewed and sometimes modified to fit project conventions (e.g., existing exception messages, naming). The developer retained full control over the final implementation.