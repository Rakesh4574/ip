package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

/**
 * Resets the completion status of a specific task.
 */
public class UnmarkAsDoneCommand extends Command {
    private int index;

    /**
     * Creates an UnmarkAsDoneCommand targeting the provided index.
     *
     * @param index Zero-based index of the task to unmark.
     */
    public UnmarkAsDoneCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.unmarkAsDone(storage, index);
        return ui.printUnmarkAsDone(task);
    }
}
