package groot.task;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents a deadline that must be done by a certain date.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String name, LocalDateTime by) {
        super(name, 'D');
        this.by = by;
    }

    public Deadline(String name, LocalDateTime by, boolean isTaskDone) {
        super(name, 'D', isTaskDone);
        this.by = by;
    }

    public Deadline(String name, LocalDateTime by, boolean isTaskDone, Set<String> tags) {
        super(name, 'D', isTaskDone, tags);
        this.by = by;
    }

    @Override
    public String getStatus() {
        return String.format("%s (by: %s)", super.getStatus(), by.format(DATE_DISPLAY_FORMAT));
    }

    @Override
    public String dataInputString() {
        return super.dataInputString() + " | " + by.format(DATE_DATA_FORMAT);
    }

    @Override
    protected boolean hasSameDetails(Task other) {
        if (!(other instanceof Deadline)) {
            return false;
        }
        Deadline otherDeadline = (Deadline) other;
        return by.equals(otherDeadline.by);
    }
}
