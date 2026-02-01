import java.time.LocalDate;
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
            } catch (Exception e) {
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

    private void handleCommand(String input) throws Exception {
        if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("mark ")) {
            markTask(input, true);
        } else if (input.startsWith("unmark ")) {
            markTask(input, false);
        } else if (input.startsWith("todo ")) {
            addTodo(input);
        } else if (input.startsWith("deadline ")) {
            addDeadline(input);
        } else {
            throw new GrootException("I don't understand that command.");
        }
    }

    private void addTodo(String input) {
        Todo t = new Todo(input.substring(5).trim());
        tasks.add(t);
        added(t);
    }

    private void addDeadline(String input) throws Exception {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2) {
            throw new GrootException("Deadline must have /by yyyy-MM-dd");
        }

        LocalDate date = Deadline.parseDate(parts[1]);
        Deadline d = new Deadline(parts[0].trim(), date);
        tasks.add(d);
        added(d);
    }

    private void listTasks() {
        System.out.println(LINE);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    private void markTask(String input, boolean done) throws Exception {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new GrootException("Invalid task number.");
        }

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
