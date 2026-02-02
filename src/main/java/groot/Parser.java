package groot;

import java.time.LocalDate;

import groot.storage.Storage;
import groot.task.Deadline;
import groot.task.Event;
import groot.task.Task;
import groot.task.TaskList;
import groot.task.Todo;
import groot.ui.Ui;

/**
 * Deals with making sense of the user command.
 * The <code>Parser</code> class interprets the user input and triggers the
 * appropriate responses and task list modifications.
 */
public class Parser {

    /**
     * Parses the full user input and executes the corresponding command.
     *
     * @param input   The raw string entered by the user.
     * @param tasks   The list of tasks to be modified.
     * @param ui      The user interface to display output messages.
     * @param storage The storage handler to save changes.
     * @return true if the command is "bye" (exits the app), false otherwise.
     * @throws GrootException If the command is invalid or the parameters are incorrect.
     */
    public static boolean parse(String input, TaskList tasks, Ui ui, Storage storage) throws GrootException {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case "bye":
                ui.showMessage("Bye. Hope to see you again soon!");
                return true;
            case "list":
                ui.showMessage("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    ui.showMessage((i + 1) + "." + tasks.get(i));
                }
                break;
            case "todo":
                handleTodo(input, tasks, ui);
                break;
            case "deadline":
                handleDeadline(input, tasks, ui);
                break;
            case "event":
                handleEvent(input, tasks, ui);
                break;
            case "mark":
            case "unmark":
                handleMark(input, tasks, ui, command.equals("mark"));
                break;
            case "delete":
                handleDelete(input, tasks, ui);
                break;
            default:
                throw new GrootException("I am Groot? (I don't know what that means)");
        }
        storage.save(tasks.getAll());
        return false;
    }

    /**
     * Handles the creation and addition of a Todo task.
     *
     * @param input The raw user input string.
     * @param tasks The TaskList to add the task to.
     * @param ui    The Ui to confirm addition.
     * @throws GrootException If the description is empty.
     */
    private static void handleTodo(String input, TaskList tasks, Ui ui) throws GrootException {
        if (input.length() <= 5) {
            throw new GrootException("Todo description is empty!");
        }
        Task t = new Todo(input.substring(5).trim());
        tasks.add(t);
        ui.showMessage("Added: " + t + "\n Now you have " + tasks.size() + " tasks.");
    }

    /**
     * Handles the creation and addition of a Deadline task.
     * Expects input in the format: deadline [desc] /by [yyyy-mm-dd].
     *
     * @param input The raw user input string.
     * @param tasks The TaskList to add the task to.
     * @param ui    The Ui to confirm addition.
     * @throws GrootException If formatting or date parsing fails.
     */
    private static void handleDeadline(String input, TaskList tasks, Ui ui) throws GrootException {
        try {
            String[] parts = input.substring(9).split(" /by ");
            LocalDate date = LocalDate.parse(parts[1].trim());
            Task t = new Deadline(parts[0].trim(), date);
            tasks.add(t);
            ui.showMessage("Added: " + t);
        } catch (Exception e) {
            throw new GrootException("Use: deadline <desc> /by yyyy-mm-dd");
        }
    }

    /**
     * Handles the creation and addition of an Event task.
     * Expects input in the format: event [desc] /from [start] /to [end].
     *
     * @param input The raw user input string.
     * @param tasks The TaskList to add the task to.
     * @param ui    The Ui to confirm addition.
     * @throws GrootException If formatting fails.
     */
    private static void handleEvent(String input, TaskList tasks, Ui ui) throws GrootException {
        try {
            String[] parts = input.substring(6).split(" /from | /to ");
            Task t = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            tasks.add(t);
            ui.showMessage("Added: " + t);
        } catch (Exception e) {
            throw new GrootException("Use: event <desc> /from <start> /to <end>");
        }
    }

    /**
     * Updates the status of a task as either marked (done) or unmarked (not done).
     *
     * @param input  The raw user input string.
     * @param tasks  The TaskList containing the task.
     * @param ui     The Ui to display the update status.
     * @param isDone True to mark as done, false to unmark.
     * @throws GrootException If the task index is invalid.
     */
    private static void handleMark(String input, TaskList tasks, Ui ui, boolean isDone) throws GrootException {
        int idx = Integer.parseInt(input.split(" ")[1]) - 1;
        Task t = tasks.get(idx);
        if (isDone) {
            t.markAsDone();
        } else {
            t.markAsNotDone();
        }
        ui.showMessage("Updated:\n   " + t);
    }

    /**
     * Removes a task from the list based on the user-provided index.
     *
     * @param input The raw user input string.
     * @param tasks The TaskList to remove from.
     * @param ui    The Ui to confirm deletion.
     * @throws GrootException If the index is invalid.
     */
    private static void handleDelete(String input, TaskList tasks, Ui ui) throws GrootException {
        int idx = Integer.parseInt(input.split(" ")[1]) - 1;
        Task t = tasks.remove(idx);
        ui.showMessage("Pruned: " + t + "\n Now you have " + tasks.size() + " tasks.");
    }
}