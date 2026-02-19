package command;

import groot.Storage;
import groot.Ui;
import groot.task.Task;
import groot.task.TaskList;

/**
 * Removes a tag from the specified task and persists the update.
 */
public class UntagCommand extends Command {
    private final int index;
    private final String tag;

    /**
     * Constructs an UntagCommand for the given task index and tag.
     *
     * @param index Zero-based target task index.
     * @param tag   The tag to remove.
     */
    public UntagCommand(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        Task task = tasks.getTask(index);
        task.removeTag(tag);
        storage.updateDataFile(tasks);
        return "I am Groot. Removed tag " + tag.trim().toLowerCase() + ":\n" + task.getStatus();
    }
}
