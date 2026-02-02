import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private static final String LINE = "____________________________________________________________";

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Groot.");
        System.out.println(" What shall we grow today?");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(" OOPS! " + message);
    }

    public void showMessage(String message) {
        System.out.println(" " + message);
    }

    public String readCommand() {
        return sc.nextLine();
    }
}