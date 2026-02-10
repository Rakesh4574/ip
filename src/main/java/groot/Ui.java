package groot;

import java.util.ArrayList;
import groot.task.Task;

public class Ui {
    public String printWelcomeMessage() {
        return "Hello! I am Groot! What can I do for you?\n";
    }

    public String printTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "I am Groot. Your list is currently empty.";
        }
        StringBuilder response = new StringBuilder("I am Groot: \n");
        for (int i = 0; i < tasks.size(); i++) {
            response.append((i + 1)).append(". ").append(tasks.get(i).printTask()).append("\n");
        }
        return response.toString();
    }

    public String printMarkAsDone(Task task) {
        return "I am Groot! Nice! I've marked this task as done:\n" + task.getStatus();
    }

    public String printUnmarkAsDone(Task task) {
        return "I am Groot. OK, I've marked this task as not done yet:\n" + task.getStatus();
    }

    public String printAddTask(Task task) {
        return String.format("I am Groot. Got it. I've added this task:\n %s\nNow you have %d tasks.",
                task.getStatus(), Task.totalTask());
    }

    public String printDeleteTask(Task task) {
        return "I am Groot. Noted. I've removed this task:\n" + task.getStatus() +
                "\nNow you have " + Task.totalTask() + " tasks.";
    }

    public String printByeMessage() {
        return "I am Groot! Bye. Hope to see you again soon!";
    }
}