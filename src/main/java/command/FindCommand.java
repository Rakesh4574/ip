package command;

import java.util.ArrayList;
import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

public class FindCommand extends Command {
    private String name;

    public FindCommand(String name) {
        this.name = name;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        ArrayList<Task> foundTasks = tasks.findTask(this.name);
        return ui.printTasks(foundTasks);
    }
}