package groot.task;

/**
 * Represents the types of tasks available in the Groot application.
 * Each type is associated with a specific shorthand icon used for
 * display and storage purposes.
 */
public enum TaskType {
    /** A simple task without any date/time constraints. */
    TODO("T"),

    /** A task that needs to be done before a specific date/time. */
    DEADLINE("D"),

    /** A task that occurs during a specific time range. */
    EVENT("E");

    /** The shorthand string representation of the task type. */
    private final String icon;

    /**
     * Initializes a TaskType with its corresponding icon.
     *
     * @param icon The shorthand character used for this task type.
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the shorthand icon associated with this task type.
     *
     * @return The icon string (e.g., "T", "D", or "E").
     */
    public String getIcon() {
        return icon;
    }
}
