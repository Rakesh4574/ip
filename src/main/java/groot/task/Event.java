package groot.task;

import java.time.LocalDateTime;
import java.util.Set;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name, 'E');
        this.from = from;
        this.to = to;
    }

    public Event(String name, LocalDateTime from, LocalDateTime to, boolean isTaskDone) {
        super(name, 'E', isTaskDone);
        this.from = from;
        this.to = to;
    }

    public Event(String name, LocalDateTime from, LocalDateTime to, boolean isTaskDone, Set<String> tags) {
        super(name, 'E', isTaskDone, tags);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getStatus() {
        return String.format("%s (from: %s to: %s)", super.getStatus(),
                from.format(DATE_DISPLAY_FORMAT), to.format(DATE_DISPLAY_FORMAT));
    }

    @Override
    public String dataInputString() {
        return super.dataInputString() + " | " + from.format(DATE_DATA_FORMAT) + " | " + to.format(DATE_DATA_FORMAT);
    }
}