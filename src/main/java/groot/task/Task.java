package groot.task;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Task {
    public static final DateTimeFormatter DATE_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public static final DateTimeFormatter DATE_DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static int count = 0;
    protected String name;
    protected boolean isDone;
    protected int idx;
    protected char type;
    // AI-assisted: Cursor helped design tag storage and addTag/removeTag for C-Tagging
    protected Set<String> tags;

    public Task(String name, char type) {
        this.name = name;
        this.isDone = false;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public Task(String name, char type, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public Task(String name, char type, boolean isDone, Set<String> tags) {
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
    }

    public void addTag(String tag) {
        String normalized = normalizeTag(tag);
        if (normalized != null && !normalized.isBlank()) {
            tags.add(normalized);
        }
    }

    private String normalizeTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return null;
        }
        return tag.trim().toLowerCase().replaceFirst("^#+", "");
    }

    public void removeTag(String tag) {
        String normalized = normalizeTag(tag);
        if (normalized != null && !normalized.isBlank()) {
            tags.remove(normalized);
        }
    }

    public boolean hasTag(String tag) {
        String normalized = normalizeTag(tag);
        return normalized != null && !normalized.isBlank() && tags.contains(normalized);
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public void markAsDone() { this.isDone = true; }
    public void unmarkAsDone() { this.isDone = false; }
    public String printTask() { return String.format("%d. %s", this.idx, getStatus()); }
    public static int totalTask() { return count; }
    public static void reduceTask() { count--; }
    public void reduceIndex() { this.idx--; }

    private String tagsToString() {
        if (tags.isEmpty()) {
            return "";
        }
        return " " + tags.stream().sorted().map(t -> "#" + t).collect(Collectors.joining(" "));
    }

    public String getStatus() {
        return String.format("[%c][%s] %s%s", type, (isDone ? "x" : " "), name, tagsToString());
    }

    public String dataInputString() {
        String base = type + " | " + (isDone ? "1" : "0") + " | " + name;
        if (tags.isEmpty()) {
            return base;
        }
        return base + " | " + tags.stream().sorted().collect(Collectors.joining(","));
    }
}