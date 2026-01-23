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
            String input = sc.nextLine();
            System.out.println("__________________________________________________");
            System.out.println(input);
            System.out.println("__________________________________________________");
        }
    }
}


