package command;

import groot.Storage;
import groot.task.TaskList;
import groot.Ui;

/**
 * Base class for all commands that Groot can execute in response to user input.
 * Each command encapsulates its own behavior and is responsible for interacting
 * with the task list, UI, and storage as needed.
 */
public abstract class Command {
    /**
     * Executes the command using the provided task list, UI, and storage
     * adapters.
     *
     * @param tasks   The current list of tasks managed by the application.
     * @param ui      The UI helper used to format responses shown to the user.
     * @param storage The storage helper used to persist changes.
     * @return A response string that should be shown to the user.
     * @throws Exception If execution fails (e.g., invalid index or storage error).
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws Exception;
}
