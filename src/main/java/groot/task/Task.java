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
    protected Set<String> tags;

    public Task(String name, char type) {
        assert name != null : "task name must not be null";
        assert type == 'T' || type == 'D' || type == 'E' : "task type must be T, D, or E";
        this.name = name;
        this.isDone = false;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public Task(String name, char type, boolean isDone) {
        assert name != null : "task name must not be null";
        assert type == 'T' || type == 'D' || type == 'E' : "task type must be T, D, or E";
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public Task(String name, char type, boolean isDone, Set<String> tags) {
        assert name != null : "task name must not be null";
        assert type == 'T' || type == 'D' || type == 'E' : "task type must be T, D, or E";
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
    }

    public void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.trim().toLowerCase());
        }
    }

    public void removeTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.remove(tag.trim().toLowerCase());
        }
    }

    public boolean hasTag(String tag) {
        return tag != null && !tag.isBlank() && tags.contains(tag.trim().toLowerCase());
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public String getName() {
        return name;
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