package groot.task;

/**
 * Represents a generic task in the Groot application.
 * This is an abstract class that provides the foundation for specific task types
 * like Todo, Deadline, and Event.
 */
public abstract class Task {
    /** The text description of the task. */
    protected String description;

    /** The completion status of the task. */
    protected boolean isDone;

    /**
     * Initializes a new Task with the given description.
     * By default, a new task is not completed.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a status icon representing whether the task is done.
     * * @return "X" if the task is done, otherwise a blank space " ".
     */
    protected String statusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns a string formatted for saving the task to a data file.
     * * @return A serialized string representation of the task.
     */
    public abstract String serialize();
}
