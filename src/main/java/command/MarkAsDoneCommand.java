package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

public class MarkAsDoneCommand extends Command {
    private int index;

    public MarkAsDoneCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.markAsDone(storage, index);
        return ui.printMarkAsDone(task);
    }
}