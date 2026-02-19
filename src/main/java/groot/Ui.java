package groot;

import java.util.ArrayList;
import groot.task.Task;

/**
 * Helper for formatting Groot's textual responses to the user.
 */
public class Ui {
    /**
     * Returns the welcome prompt shown on startup.
     *
     * @return The welcome message string.
     */
    public String printWelcomeMessage() {
        return "Hello! I am Groot! What can I do for you?\n";
    }

    /**
     * Formats a numbered list of tasks, or a message when none exist.
     *
     * @param tasks Tasks to display.
     * @return A multi-line task list representation.
     */
    public String printTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "I am Groot. Your list is currently empty.";
        }
        StringBuilder response = new StringBuilder("I am Groot: \n");
        for (int i = 0; i < tasks.size(); i++) {
            response.append((i + 1)).append(". ").append(tasks.get(i).getStatus()).append("\n");
        }
        return response.toString();
    }

    /**
     * Formats a response after marking a task as done.
     *
     * @param task The task that was marked complete.
     * @return The confirmation message.
     */
    public String printMarkAsDone(Task task) {
        return "I am Groot! Nice! I've marked this task as done:\n" + task.getStatus();
    }

    /**
     * Formats a response after unmarking a task.
     *
     * @param task The task that was unmarked.
     * @return The confirmation message.
     */
    public String printUnmarkAsDone(Task task) {
        return "I am Groot. OK, I've marked this task as not done yet:\n" + task.getStatus();
    }

    /**
     * Formats a response after adding a task to the list.
     *
     * @param task The newly added task.
     * @return The confirmation message including the current task count.
     */
    public String printAddTask(Task task) {
        return String.format("I am Groot. Got it. I've added this task:\n %s\nNow you have %d tasks.",
                task.getStatus(), Task.totalTask());
    }

    /**
     * Formats a response after deleting a task.
     *
     * @param task The removed task.
     * @return The confirmation message including the remaining task count.
     */
    public String printDeleteTask(Task task) {
        return "I am Groot. Noted. I've removed this task:\n" + task.getStatus() +
                "\nNow you have " + Task.totalTask() + " tasks.";
    }

    /**
     * Returns the farewell message shown when the user exits.
     *
     * @return The goodbye message.
     */
    public String printByeMessage() {
        return "I am Groot! Bye. Hope to see you again soon!";
    }
}
