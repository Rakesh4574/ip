package groot.task;

/**
 * Represents a simple task without any date or time attached to it.
 * A <code>Todo</code> object contains a description and a completion status.
 */
public class Todo extends Task {

    /**
     * Initializes a new Todo task with the specified description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task, including
     * its status icon and type identifier [T].
     *
     * @return A formatted string representing the todo task.
     */
    @Override
    public String toString() {
        return "[T][" + statusIcon() + "] " + description;
    }

    /**
     * Returns a serialized version of the todo task for file storage.
     *
     * @return A string formatted for storage in the data file (e.g., "T | 0 | read book").
     */
    @Override
    public String serialize() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
