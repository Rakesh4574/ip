import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate by;
    private static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + statusIcon() + "] " + description
                + " (by: " + by.format(OUTPUT) + ")";
    }

    @Override
    public String serialize() {
        return "D | " + (isDone ? "1" : "0") + " | "
                + description + " | " + by;
    }

    public static LocalDate parseDate(String input) {
        return LocalDate.parse(input.trim());
    }
}
