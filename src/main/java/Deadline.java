public class Deadline extends Task {
    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + statusIcon() + "] " + description + " (by: " + by + ")";
    }

    @Override
    public String serialize() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
