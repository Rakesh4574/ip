package groot;

import java.util.Scanner;

/**
 * Lightweight console launcher that powers the I/O redirection tests.
 */
public class TextLauncher {
    /**
     * Entry point for the text UI: reads stdin, writes stdout, and exits on "bye".
     *
     * @param args Ignored.
     */
    public static void main(String[] args) {
        Groot groot = new Groot();
        Scanner sc = new Scanner(System.in);

        System.out.print(groot.getWelcomeMessage());

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            System.out.println(groot.getResponse(input));
            if (input.trim().equalsIgnoreCase("bye")) {
                break;
            }
        }
    }
}
