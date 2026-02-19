package command;

import java.time.LocalDateTime;
import groot.task.Deadline;
import groot.task.Event;
import groot.task.Task;
import groot.task.TaskList;
import groot.task.ToDo;
import groot.Storage;
import groot.Ui;

/**
 * Creates tasks based on the user command type (todo, deadline, event)
 * and adds them to the task list.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand for a simple todo task.
     *
     * @param name The description of the todo.
     */
    public AddCommand(String name) {
        this.task = new ToDo(name);
    }

    /**
     * Constructs an AddCommand for a deadline task.
     *
     * @param name The description of the deadline.
     * @param by   The deadline date/time.
     */
    public AddCommand(String name, LocalDateTime by) {
        this.task = new Deadline(name, by);
    }

    /**
     * Constructs an AddCommand for an event task.
     *
     * @param name The description of the event.
     * @param from The start date/time of the event.
     * @param to   The end date/time of the event.
     */
    public AddCommand(String name, LocalDateTime from, LocalDateTime to) {
        this.task = new Event(name, from, to);
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.addTask(storage, task);
        return ui.printAddTask(task);
    }
}
