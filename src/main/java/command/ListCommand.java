package command;

import groot.Storage;
import groot.task.TaskList;
import groot.Ui;

/**
 * Displays all tasks currently stored in the task list.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.printTasks(taskList.get());
    }
}
