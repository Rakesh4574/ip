package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task deletedTask = tasks.deleteTask(storage, index);
        return ui.printDeleteTask(deletedTask);
    }
}