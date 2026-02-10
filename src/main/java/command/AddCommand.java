package command;

import java.time.LocalDateTime;
import groot.task.Deadline;
import groot.task.Event;
import groot.task.Task;
import groot.task.TaskList;
import groot.task.ToDo;
import groot.Storage;
import groot.Ui;

public class AddCommand extends Command {
    private Task task;

    public AddCommand(String name) {
        this.task = new ToDo(name);
    }

    public AddCommand(String name, LocalDateTime by) {
        this.task = new Deadline(name, by);
    }

    public AddCommand(String name, LocalDateTime from, LocalDateTime to) {
        this.task = new Event(name, from, to);
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.addTask(storage, task);
        return ui.printAddTask(task);
    }
}
