import java.util.ArrayList;
import java.util.Scanner;

public class Groot {

    private ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        new Groot().run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        printGreeting();

        while (true) {
            try {
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("bye")) {
                    printFarewell();
                    break;
                }

                handleCommand(input);

            } catch (GrootException e) {
                printLine();
                System.out.println(" " + e.getMessage());
                printLine();
            }
        }
    }

    private void handleCommand(String input) throws GrootException {
        if (input.startsWith("todo")) {
            handleTodo(input);
        } else if (input.startsWith("deadline")) {
            handleDeadline(input);
        } else if (input.startsWith("event")) {
            handleEvent(input);
        } else if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            markTask(input, true);
        } else if (input.startsWith("unmark")) {
            markTask(input, false);
        } else if (input.startsWith("delete")) {
                deleteTask(input);
        } else {
            throw new GrootException("I don't understand that command.");
        }
    }

    private void handleTodo(String input) throws GrootException {
        if (input.equals("todo")) {
            throw new GrootException("A todo needs a description.");
        }

        Task task = new Todo(input.substring(5).trim());
        tasks.add(task);
        printAdd(task);
    }

    private void handleDeadline(String input) throws GrootException {
        if (!input.contains("/by")) {
            throw new GrootException("A deadline needs a /by time.");
        }

        String[] parts = input.substring(9).split("/by", 2);
        Task task = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(task);
        printAdd(task);
    }

    private void handleEvent(String input) throws GrootException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new GrootException("An event needs /from and /to.");
        }

        String description = input.substring(6, input.indexOf("/from")).trim();
        String from = input.substring(input.indexOf("/from") + 5, input.indexOf("/to")).trim();
        String to = input.substring(input.indexOf("/to") + 3).trim();

        Task task = new Event(description, from, to);
        tasks.add(task);
        printAdd(task);
    }

    private void markTask(String input, boolean done) throws GrootException {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            Task task = tasks.get(index);

            if (done) {
                task.markAsDone();
                printStatus("Nice! I've marked this task as done:", task);
            } else {
                task.markAsNotDone();
                printStatus("OK, I've marked this task as not done yet:", task);
            }
        } catch (Exception e) {
            throw new GrootException("Please specify a valid task number.");
        }
    }

    private void deleteTask(String input) throws GrootException {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            Task removed = tasks.remove(index);

            printLine();
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            printLine();

        } catch (Exception e) {
            throw new GrootException("Please specify a valid task number to delete.");
        }
    }

    private void listTasks() {
        printLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    private void printAdd(Task task) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private void printStatus(String msg, Task task) {
        printLine();
        System.out.println(" " + msg);
        System.out.println("   " + task);
        printLine();
    }

    private void printGreeting() {
        printLine();
        System.out.println(" Hello! I'm Groot.");
        System.out.println(" What shall we grow today?");
        printLine();
    }

    private void printFarewell() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    private void printLine() {
        System.out.println("__________________________________________________");
    }
}
