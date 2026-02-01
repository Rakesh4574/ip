import java.util.ArrayList;
import java.util.Scanner;

public class Groot {
    private static final String LINE = "__________________________________________________";

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage("data/groot.txt");

    public static void main(String[] args) {
        new Groot().run();
    }

    private void run() {
        storage.load(tasks);

        Scanner sc = new Scanner(System.in);
        greet();

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                sayBye();
                break;
            }

            try {
                handleCommand(input);
                storage.save(tasks);
            } catch (GrootException e) {
                System.out.println(LINE);
                System.out.println(" " + e.getMessage());
                System.out.println(LINE);
            }
        }
    }

    private void greet() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Groot.");
        System.out.println(" What shall we grow today?");
        System.out.println(LINE);
    }

    private void sayBye() {
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private void handleCommand(String input) throws GrootException {
        if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("mark ")) {
            markTask(input, true);
        } else if (input.startsWith("unmark ")) {
            markTask(input, false);
        } else if (input.startsWith("todo ")) {
            tasks.add(new Todo(input.substring(5).trim()));
            added(tasks.get(tasks.size() - 1));
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ", 2);
            if (parts.length < 2) throw new GrootException("Deadline needs /by");
            tasks.add(new Deadline(parts[0], parts[1]));
            added(tasks.get(tasks.size() - 1));
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from | /to ", 3);
            if (parts.length < 3) throw new GrootException("Event needs /from and /to");
            tasks.add(new Event(parts[0], parts[1], parts[2]));
            added(tasks.get(tasks.size() - 1));
        } else {
            throw new GrootException("I'm not sure what you mean");
        }
    }

    private void listTasks() {
        System.out.println(LINE);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    private void markTask(String input, boolean done) throws GrootException {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index < 0 || index >= tasks.size()) throw new GrootException("Invalid task number");

        if (done) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }

        System.out.println(LINE);
        System.out.println(" Updated:");
        System.out.println("  " + tasks.get(index));
        System.out.println(LINE);
    }

    private void added(Task task) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks.");
        System.out.println(LINE);
    }
}
