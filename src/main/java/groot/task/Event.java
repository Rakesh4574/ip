package groot.task;

/**
 * Represents an event task with a start and end time.
 * An <code>Event</code> object includes a description, a starting time/date,
 * and an ending time/date.
 */
public class Event extends Task {
    /** The starting time or date of the event. */
    private final String from;

    /** The ending time or date of the event. */
    private final String to;

    /**
     * Initializes a new Event task.
     * * @param description The description of the event.
     * @param from The start time/date of the event.
     * @param to The end time/date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event task, including
     * its status icon and the duration (from/to).
     * * @return A formatted string representing the event.
     */
    @Override
    public String toString() {
        return "[E][" + statusIcon() + "] " + description +
                " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns a serialized version of the event task for file storage.
     * * @return A string formatted for storage in the data file.
     */
    @Override
    public String serialize() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
