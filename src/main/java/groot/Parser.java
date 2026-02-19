package groot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import command.Command;
import command.*;
import groot.GrootException;
import groot.task.Task;

/**
 * Responsible for interpreting raw user commands and instantiating the corresponding Command objects.
 */
public class Parser {
    /**
     * Formatter used to read/write dates in yyyy-MM-dd format.
     */
    public static final DateTimeFormatter DATE_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses the provided command string and returns the matching {@link Command} instance.
     *
     * @param command The trimmed user input to parse.
     * @return A Command that encapsulates the desired operation.
     * @throws Exception When the command or its arguments are invalid.
     */
    public static Command parse(String command) throws Exception {
        Scanner sc = new Scanner(command);
        if (!sc.hasNext()) {
            throw new GrootException("I am Groot? (Invalid Command!)");
        }

        String input = sc.next();

        switch (input) {
            case "list":
                return new ListCommand();

            case "mark":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to Mark!");
                return new MarkAsDoneCommand(sc.nextInt() - 1);

            case "unmark":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to unMark!");
                return new UnmarkAsDoneCommand(sc.nextInt() - 1);

            case "delete":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to delete!");
                return new DeleteCommand(sc.nextInt() - 1);

            case "todo":
                String name = sc.nextLine().trim();
                if (name.isBlank()) throw new GrootException("Missing Task name!");
                return new AddCommand(name);

            case "deadline":
                String deadlineLine = sc.nextLine();
                String[] nameAndBy = deadlineLine.split(" /by ");
                if (nameAndBy.length != 2) throw new GrootException("Use: <Description> /by yyyy-MM-dd");
                try {
                    // Parse date only, then convert to LocalDateTime at 00:00
                    LocalDateTime dateTime = LocalDate.parse(nameAndBy[1].trim(), DATE_DATA_FORMATTER).atStartOfDay();
                    return new AddCommand(nameAndBy[0].trim(), dateTime);
                } catch (DateTimeParseException e) {
                    throw new GrootException("Invalid date! Use yyyy-MM-dd (e.g., 2026-02-10)");
                }

            case "event":
                String eventLine = sc.nextLine();
                String[] nameAndFrom = eventLine.split(" /from ");
                if (nameAndFrom.length != 2) throw new GrootException("Use: <Desc> /from <date> /to <date>");
                String[] fromAndTo = nameAndFrom[1].split(" /to ");
                if (fromAndTo.length != 2) throw new GrootException("Missing /to date!");
                try {
                    LocalDateTime fromDt = LocalDate.parse(fromAndTo[0].trim(), DATE_DATA_FORMATTER).atStartOfDay();
                    LocalDateTime toDt = LocalDate.parse(fromAndTo[1].trim(), DATE_DATA_FORMATTER).atStartOfDay();
                    return new AddCommand(nameAndFrom[0].trim(), fromDt, toDt);
                } catch (DateTimeParseException e) {
                    throw new GrootException("Invalid date! Use yyyy-MM-dd");
                }

            case "find":
                return new FindCommand(sc.nextLine().trim());

            case "tag":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to tag!");
                int tagIdx = sc.nextInt() - 1;
                String tagToAdd = sc.nextLine().trim();
                if (tagToAdd.isBlank()) throw new GrootException("Please specify a tag (e.g., tag 1 fun)");
                return new TagCommand(tagIdx, tagToAdd);

            case "untag":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to untag!");
                int untagIdx = sc.nextInt() - 1;
                String tagToRemove = sc.nextLine().trim();
                if (tagToRemove.isBlank()) throw new GrootException("Please specify a tag to remove");
                return new UntagCommand(untagIdx, tagToRemove);

            default:
                throw new GrootException("I am Groot. (Invalid command: " + input + ")");
        }
    }
}
