package command;

import java.util.ArrayList;
import groot.Storage;
import groot.task.Task;
import groot.task.TaskList;
import groot.Ui;

public class FindCommand extends Command {
    private final String keyword;
    private final boolean searchByTag;

    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
        this.searchByTag = this.keyword.startsWith("#");
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        ArrayList<Task> foundTasks = searchByTag
                ? tasks.findTaskByTag(keyword.substring(1))
                : tasks.findTask(keyword);
        return ui.printTasks(foundTasks);
    }
}