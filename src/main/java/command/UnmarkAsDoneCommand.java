package command;

import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

public class UnmarkAsDoneCommand extends Command {
    private int index;

    public UnmarkAsDoneCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.unmarkAsDone(storage, index);
        return ui.printUnmarkAsDone(task);
    }
}