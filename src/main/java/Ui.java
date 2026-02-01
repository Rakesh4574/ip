import java.util.Scanner;

public class Ui {
    private static final String LINE = "__________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Groot.");
        System.out.println(" What shall we grow today?");
        System.out.println(LINE);
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println(" " + message);
        System.out.println(LINE);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
