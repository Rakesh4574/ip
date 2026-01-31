import java.util.Scanner;

public class Groot {

    public static void main(String[] args) {
        new Groot().run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("""
                __________________________________________________
                 Hello! I'm Groot
                 What shall we grow today?
                __________________________________________________
                """);

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("__________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("__________________________________________________");
                break;

            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("__________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("__________________________________________________");

            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].unmark();

                System.out.println("__________________________________________________");
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index]);
                System.out.println("__________________________________________________");

            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();

                System.out.println("__________________________________________________");
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index]);
                System.out.println("__________________________________________________");

            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;

                System.out.println("__________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("__________________________________________________");
            }
        }
    }
}
