package command;

import groot.Storage;
import groot.task.TaskList;
import groot.Ui;

public abstract class Command {
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws Exception;
}