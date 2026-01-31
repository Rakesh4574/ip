import java.util.Scanner;

public class Groot {

    private static final int MAX_TASKS = 100;
    private Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

    public static void main(String[] args) {
        new Groot().run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        printWelcome();

        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                printBye();
                break;
            } else if (input.equals("list")) {
                printList();
            } else if (input.startsWith("todo ")) {
                addTodo(input.substring(5));
            } else if (input.startsWith("deadline ")) {
                addDeadline(input.substring(9));
            } else {
                echo(input);
            }
        }
    }

    private void addDeadline(String input) {
        String[] parts = input.split(" /by ", 2);
        tasks[taskCount++] = new Deadline(parts[0], parts[1]);

        System.out.println("__________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("__________________________________________________");
    }

    private void addTodo(String description) {
        tasks[taskCount++] = new Todo(description);
        System.out.println("__________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("__________________________________________________");
    }

    private void printList() {
        System.out.println("__________________________________________________");
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
        System.out.println("__________________________________________________");
    }

    private void echo(String input) {
        System.out.println("__________________________________________________");
        System.out.println(" " + input);
        System.out.println("__________________________________________________");
    }

    private void printWelcome() {
        System.out.println("__________________________________________________");
        System.out.println(" Hello! I'm Groot.");
        System.out.println(" What shall we grow today?");
        System.out.println("__________________________________________________");
    }

    private void printBye() {
        System.out.println("__________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("__________________________________________________");
    }
}
