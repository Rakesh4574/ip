package groot.task;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shared implementation for tasks. Tracks completion, tags, and display helpers.
 */
public class Task {
    /**
     * Format used when presenting dates to the user.
     */
    public static final DateTimeFormatter DATE_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Format used when persisting dates to disk.
     */
    public static final DateTimeFormatter DATE_DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static int count = 0;
    protected String name;
    protected boolean isDone;
    protected int idx;
    protected char type;
    // AI-assisted: Cursor helped design tag storage and addTag/removeTag for C-Tagging
    protected Set<String> tags;

    /**
     * Creates a task with a fresh index and an empty tag set.
     *
     * @param name Description shown to the user.
     * @param type Symbol used in lists/storage (T/D/E).
     */
    public Task(String name, char type) {
        this.name = name;
        this.isDone = false;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    /**
     * Creates a task with a known completion status.
     *
     * @param name   Task description.
     * @param type   Task symbol.
     * @param isDone Whether the task is marked as completed.
     */
    public Task(String name, char type, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    /**
     * Creates a tagged task instance with the provided metadata.
     *
     * @param name   Description text.
     * @param type   Task symbol.
     * @param isDone Completion flag.
     * @param tags   Tags already associated with the task.
     */
    public Task(String name, char type, boolean isDone, Set<String> tags) {
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
    }

    /**
     * Adds a normalized tag to the task.
     *
     * @param tag The raw tag text.
     */
    public void addTag(String tag) {
        String normalized = normalizeTag(tag);
        if (normalized != null && !normalized.isBlank()) {
            tags.add(normalized);
        }
    }

    /**
     * Helper to normalize and sanitize tag text.
     *
     * @param tag The raw tag.
     * @return Normalized tag or null when invalid.
     */
    private String normalizeTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return null;
        }
        return tag.trim().toLowerCase().replaceFirst("^#+", "");
    }

    /**
     * Removes a tag from the task if present.
     *
     * @param tag Tag text (may include leading '#').
     */
    public void removeTag(String tag) {
        String normalized = normalizeTag(tag);
        if (normalized != null && !normalized.isBlank()) {
            tags.remove(normalized);
        }
    }

    /**
     * Checks if the task has the requested tag.
     *
     * @param tag Tag to look for.
     * @return True when the tag is present.
     */
    public boolean hasTag(String tag) {
        String normalized = normalizeTag(tag);
        return normalized != null && !normalized.isBlank() && tags.contains(normalized);
    }

    /**
     * Returns an immutable copy of the tag set.
     *
     * @return The tags associated with this task.
     */
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() { this.isDone = true; }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() { this.isDone = false; }

    /**
     * Returns a printable representation including its index.
     *
     * @return Formatted numbered task line.
     */
    public String printTask() { return String.format("%d. %s", this.idx, getStatus()); }

    /**
     * Returns the total count of tasks ever created (used for numbering).
     *
     * @return The task count.
     */
    public static int totalTask() { return count; }

    /**
     * Reduces the global task count (used after deleting a task).
     */
    public static void reduceTask() { count--; }

    /**
     * Decrements this task's stored index value.
     */
    public void reduceIndex() { this.idx--; }

    /**
     * Helper to format the task's tags for display.
     *
     * @return A space-prefixed string of tags or an empty string.
     */
    private String tagsToString() {
        if (tags.isEmpty()) {
            return "";
        }
        return " " + tags.stream().sorted().map(t -> "#" + t).collect(Collectors.joining(" "));
    }

    /**
     * Builds the status string shown in the UI listing.
     *
     * @return The display-ready status, including tags.
     */
    public String getStatus() {
        return String.format("[%c][%s] %s%s", type, (isDone ? "x" : " "), name, tagsToString());
    }

    /**
     * Serializes the task for persistence in the data file.
     *
     * @return The line that should be written to storage.
     */
    public String dataInputString() {
        String base = type + " | " + (isDone ? "1" : "0") + " | " + name;
        if (tags.isEmpty()) {
            return base;
        }
        return base + " | " + tags.stream().sorted().collect(Collectors.joining(","));
    }
}
