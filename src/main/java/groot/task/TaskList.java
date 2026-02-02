package groot.task;

import groot.GrootException;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int i) throws GrootException {
        if (i < 0 || i >= tasks.size()) {
            throw new GrootException("Invalid task number. I can't prune what isn't there!");
        }
        return tasks.remove(i);
    }

    public Task get(int i) throws GrootException {
        if (i < 0 || i >= tasks.size()) {
            throw new GrootException("Invalid task number. That branch doesn't exist.");
        }
        return tasks.get(i);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Finds and returns a list of tasks whose descriptions contain the given keyword.
     *
     * @param keyword The string to search for.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}