package groot.task;

import groot.GrootException;
import groot.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around the collection of tasks that centralizes mutation and lookup logic.
 */
public class TaskList {
    private static final String DUPLICATE_TASK_MESSAGE = "This task already exists in your list.";
    private ArrayList<Task> listOfTasks;
    private List<Task> lastDisplayedView;

    /**
     * Initializes the TaskList with pre-existing tasks loaded from storage.
     *
     * @param listOfTasks The tasks read from disk or an empty list.
     */
    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
        this.lastDisplayedView = new ArrayList<>(listOfTasks);
    }

    /**
     * Returns the underlying mutable list of tasks.
     *
     * @return The task collection.
     */
    public ArrayList<Task> get() {
        return this.listOfTasks;
    }

    /**
     * Retrieves a single task without modifying the collection.
     *
     * @param index Zero-based index of the task.
     * @return The requested Task.
     */
    public Task getTask(int index) {
        return listOfTasks.get(index);
    }

    /**
     * Deletes the task at the requested index and updates persistent storage.
     *
     * @param storage Storage helper that writes the updated list.
     * @param index   Zero-based index of the task to remove.
     * @return The removed Task.
     * @throws IOException When storage writing fails.
     */
    public Task deleteTask(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        listOfTasks.remove(index);
        Task.reduceTask();

        for (int i = index; i < listOfTasks.size(); i++) {
            listOfTasks.get(i).reduceIndex();
        }

        storage.updateDataFile(this);
        removeFromView(task);
        return task;
    }

    /**
     * Adds a new task to the collection and persists it.
     *
     * @param storage Storage helper to save the updated list.
     * @param task    The task to add.
     * @throws IOException When storage writing fails.
     */
    public void addTask(Storage storage, Task task) throws IOException, GrootException {
        if (containsDuplicate(task)) {
            throw new GrootException(DUPLICATE_TASK_MESSAGE);
        }
        this.listOfTasks.add(task);
        storage.updateDataFile(this);
    }

    /**
     * Marks a task as completed and persists the change.
     *
     * @param storage Storage helper to save the updated list.
     * @param index   Zero-based index of the task to mark.
     * @return The updated Task.
     * @throws IOException When storage writing fails.
     */
    public Task markAsDone(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        task.markAsDone();
        storage.updateDataFile(this);
        return task;
    }

    /**
     * Marks a task as not done and persists the change.
     *
     * @param storage Storage helper to save the updated list.
     * @param index   Zero-based index of the task to unmark.
     * @return The updated Task.
     * @throws IOException When storage writing fails.
     */
    public Task unmarkAsDone(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        task.unmarkAsDone();
        storage.updateDataFile(this);
        return task;
    }

    /**
     * Searches for tasks whose names contain the query text.
     *
     * @param searchedName The keyword to filter task names.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> findTask(String searchedName) {
        ArrayList<Task> resultList = new ArrayList<>();
        for (Task task : listOfTasks) {
            if (task.name.toLowerCase().contains(searchedName.trim().toLowerCase())) {
                resultList.add(task);
            }
        }
        return resultList;
    }

    /**
     * Searches for tasks that contain the specified tag.
     *
     * @param tag The tag to search for (case-insensitive).
     * @return A list of matching tasks.
     */
    public ArrayList<Task> findTaskByTag(String tag) {
        ArrayList<Task> resultList = new ArrayList<>();
        String tagLower = tag.trim().toLowerCase();
        for (Task task : listOfTasks) {
            if (task.hasTag(tagLower)) {
                resultList.add(task);
            }
        }
        return resultList;
    }

    /**
     * Updates the cached view that the user most recently saw.
     *
     * @param view The list that was displayed (null resets to the full list).
     */
    public void updateDisplayedView(List<Task> view) {
        if (view == null) {
            this.lastDisplayedView = new ArrayList<>(listOfTasks);
        } else {
            this.lastDisplayedView = new ArrayList<>(view);
        }
    }

    private boolean containsDuplicate(Task candidate) {
        for (Task existing : listOfTasks) {
            if (existing.isSameTask(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a view-relative index to the current list index.
     *
     * @param viewIndex Zero-based index based on the last displayed list.
     * @return The corresponding index within the full task list.
     * @throws GrootException When the view is empty or the provided index is out of range.
     */
    public int resolveIndexFromView(int viewIndex) throws GrootException {
        if (lastDisplayedView == null || lastDisplayedView.isEmpty()) {
            throw new GrootException("The most recent list you saw is empty.");
        }
        if (viewIndex < 0 || viewIndex >= lastDisplayedView.size()) {
            throw new GrootException("Please choose a task number between 1 and " +
                    lastDisplayedView.size() + " from the list you most recently displayed.");
        }
        Task target = lastDisplayedView.get(viewIndex);
        int globalIndex = listOfTasks.indexOf(target);
        if (globalIndex == -1) {
            throw new GrootException("That task can no longer be found in the list.");
        }
        return globalIndex;
    }

    /**
     * Removes a task from the cached view (used after deletion).
     *
     * @param task Task to drop from the last displayed list.
     */
    public void removeFromView(Task task) {
        if (lastDisplayedView != null) {
            lastDisplayedView.remove(task);
        }
    }
}
