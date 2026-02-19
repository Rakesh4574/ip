# Groot User Guide

**Groot** is a desktop task management chatbot that helps you track todos, deadlines, and events. It features a simple command-line interface and persists your data locally.

## Quick Start

1. Run the application.
2. Type commands and press Enter.
3. Type `bye` to exit.

## Features

### Viewing tasks: `list`

Displays all tasks in your list.

**Format:** `list`

**Example:**
```
list
```

**Expected output:**
```
I am Groot:
1. [T][ ] Buy milk #fun
2. [D][ ] Submit report (by: 20 Feb 2026) #work
3. [E][x] Team meeting (from: 15 Feb 2026 to: 15 Feb 2026)
Now you have 3 tasks.
```

---

### Adding a todo: `todo`

Adds a simple todo task.

**Format:** `todo DESCRIPTION`

**Example:**
```
todo Buy groceries
```

**Expected output:**
```
I am Groot. Got it. I've added this task:
 [T][ ] Buy groceries
Now you have 1 tasks.
```

---

### Adding a deadline: `deadline`

Adds a task with a due date.

**Format:** `deadline DESCRIPTION /by yyyy-MM-dd`

**Example:**
```
deadline Submit report /by 2026-02-20
```

**Expected output:**
```
I am Groot. Got it. I've added this task:
 [D][ ] Submit report (by: 20 Feb 2026)
Now you have 2 tasks.
```

---

### Adding an event: `event`

Adds a task with a start and end date.

**Format:** `event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd`

**Example:**
```
event Team meeting /from 2026-02-15 /to 2026-02-15
```

**Expected output:**
```
I am Groot. Got it. I've added this task:
 [E][ ] Team meeting (from: 15 Feb 2026 to: 15 Feb 2026)
Now you have 3 tasks.
```

---

### Marking a task as done: `mark`

Marks the task at the given index as done.

**Format:** `mark INDEX`

**Example:**
```
mark 1
```

**Expected output:**
```
I am Groot! Nice! I've marked this task as done:
[T][x] Buy groceries
```

---

### Unmarking a task: `unmark`

Marks the task at the given index as not done yet.

**Format:** `unmark INDEX`

**Example:**
```
unmark 1
```

---

### Deleting a task: `delete`

Removes the task at the given index.

**Format:** `delete INDEX`

**Example:**
```
delete 2
```

---

### Finding tasks: `find`

Searches for tasks by keyword or by tag.

**Format:** `find KEYWORD` or `find #TAG`

**Examples:**
```
find report
```
Finds all tasks whose description contains "report" (case-insensitive).

```
find #work
```
Finds all tasks tagged with `#work`.

---

### Tagging a task: `tag`

Adds a tag to a task. Tags are shown as `#tagname` in the task list.

**Format:** `tag INDEX TAG`

**Example:**
```
tag 1 fun
```

**Expected output:**
```
I am Groot. Tagged task as #fun:
[T][ ] Buy milk #fun
```

---

### Removing a tag: `untag`

Removes a tag from a task.

**Format:** `untag INDEX TAG`

**Example:**
```
untag 1 fun
```

---

### Exiting: `bye`

Exits the application.

**Format:** `bye`

---

## Command Summary

| Command   | Format                                          | Description                    |
|-----------|--------------------------------------------------|--------------------------------|
| `list`    | `list`                                          | List all tasks                 |
| `todo`    | `todo DESCRIPTION`                              | Add a todo                     |
| `deadline`| `deadline DESC /by yyyy-MM-dd`                   | Add a deadline                 |
| `event`   | `event DESC /from yyyy-MM-dd /to yyyy-MM-dd`     | Add an event                   |
| `mark`    | `mark INDEX`                                    | Mark task as done              |
| `unmark`  | `unmark INDEX`                                  | Unmark task                    |
| `delete`  | `delete INDEX`                                  | Delete a task                  |
| `find`    | `find KEYWORD` or `find #TAG`                    | Search by keyword or tag       |
| `tag`     | `tag INDEX TAG`                                 | Add a tag to a task            |
| `untag`   | `untag INDEX TAG`                               | Remove a tag from a task       |
| `bye`     | `bye`                                           | Exit the application           |

## Notes

- **INDEX** is the 1-based number shown in the task list.
- **Parameters** can be supplied in any order where multiple are expected.
- Tags are case-insensitive and stored in lowercase.
- Dates must be in `yyyy-MM-dd` format (e.g., `2026-02-20`).
