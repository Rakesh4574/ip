package groot.task;

import groot.GrootException;
import java.util.ArrayList;

/**
 * Manages an internal list of tasks.
 * Provides functionality to add, remove, retrieve, and query the size of the task collection.
 */
public class TaskList {
    /** The internal list used to store Task objects. */
    private final ArrayList<Task> tasks;

    /**
     * Initializes an empty TaskList.
     */
    public TaskList() {

        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes a TaskList with an existing collection of tasks.
     *
     * @param tasks An ArrayList of Task objects to populate the list.
     */
    public TaskList(ArrayList<Task> tasks) {

        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t The task to be added.
     */
    public void add(Task t) {

        tasks.add(t);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param i The index of the task to be removed (0-based).
     * @return The task that was removed.
     * @throws GrootException If the index is out of the valid range of the list.
     */
    public Task remove(int i) throws GrootException {
        if (i < 0 || i >= tasks.size()) {
            throw new GrootException("Invalid task number. I can't prune what isn't there!");
        }
        return tasks.remove(i);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param i The index of the task to retrieve (0-based).
     * @return The task at the specified index.
     * @throws GrootException If the index is out of the valid range of the list.
     */
    public Task get(int i) throws GrootException {
        if (i < 0 || i >= tasks.size()) {
            throw new GrootException("Invalid task number. That branch doesn't exist.");
        }
        return tasks.get(i);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int size() {

        return tasks.size();
    }

    /**
     * Returns the underlying list of all tasks.
     *
     * @return An ArrayList containing all tasks in this list.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}