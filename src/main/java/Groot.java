import java.util.Scanner;

public class Groot {

    public static void main(String[] args) {
        new Groot().run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                __________________________________________________
                 Hello! I am Groot. Guardian of your tasks.
                 What shall we grow today?
                __________________________________________________
                """);

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("__________________________________________________");
                System.out.println(" Bye. Until next time!");
                System.out.println("__________________________________________________");
                break;
            } else {
                System.out.println("__________________________________________________");
                System.out.println(input);
                System.out.println("__________________________________________________");
            }
        }
    }
}


