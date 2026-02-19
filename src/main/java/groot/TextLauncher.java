package groot;

import java.util.Scanner;

/**
 * Text-based launcher for Groot that reads from stdin and writes to stdout.
 * Used for I/O redirection testing.
 */
public class TextLauncher {
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
