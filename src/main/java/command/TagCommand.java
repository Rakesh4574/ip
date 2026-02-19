package command;

import groot.Storage;
import groot.Ui;
import groot.task.Task;
import groot.task.TaskList;

public class TagCommand extends Command {
    private final int index;
    private final String tag;

    public TagCommand(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.getTask(index);
        task.addTag(tag);
        storage.updateDataFile(tasks);
        return "I am Groot. Tagged task as #" + tag.trim().toLowerCase() + ":\n" + task.getStatus();
    }
}
