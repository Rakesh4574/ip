package groot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * A <code>Deadline</code> object contains a description and a date by which
 * the task must be completed.
 */
public class Deadline extends Task {
    /** The date the task is due. */
    private final LocalDate by;
    /** Formatter for displaying the date in a user-friendly format. */
    private static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Initializes a new Deadline task.
     * * @param description The description of the task.
     * @param by The date the task is due.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task, including
     * its status icon and formatted due date.
     * * @return A formatted string representing the deadline.
     */
    @Override
    public String toString() {
        return "[D][" + statusIcon() + "] " + description
                + " (by: " + by.format(OUTPUT) + ")";
    }

    /**
     * Returns a serialized version of the deadline task for file storage.
     * * @return A string formatted for storage in the data file.
     */
    @Override
    public String serialize() {
        return "D | " + (isDone ? "1" : "0") + " | "
                + description + " | " + by;
    }

    /**
     * Parses a date string into a LocalDate object.
     * * @param input The date string in yyyy-mm-dd format.
     * @return The corresponding LocalDate object.
     */
    public static LocalDate parseDate(String input) {
        return LocalDate.parse(input.trim());
    }
}
