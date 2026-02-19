package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

/**
 * Marks a specific task as completed.
 */
public class MarkAsDoneCommand extends Command {
    private int index;

    /**
     * Creates a command that will mark the task at the given zero-based index.
     *
     * @param index Index of the task to mark as done.
     */
    public MarkAsDoneCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.markAsDone(storage, index);
        return ui.printMarkAsDone(task);
    }
}
