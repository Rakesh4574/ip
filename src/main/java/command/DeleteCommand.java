package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

/**
 * Removes a task at the specified index from the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a DeleteCommand that targets the given task index.
     *
     * @param index Zero-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        int actualIndex = tasks.resolveIndexFromView(index);
        Task deletedTask = tasks.deleteTask(storage, actualIndex);
        return ui.printDeleteTask(deletedTask, tasks.get().size());
    }
}
