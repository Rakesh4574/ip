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

public class Parser {
    public static final DateTimeFormatter DATE_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Command parse(String command) throws Exception {
        assert command != null : "command string must not be null";
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

            case "tag": {
                IndexAndTag parsed = parseIndexAndTag(sc, "tag", "add");
                return new TagCommand(parsed.index, parsed.tag);
            }
            case "untag": {
                IndexAndTag parsed = parseIndexAndTag(sc, "untag", "remove");
                return new UntagCommand(parsed.index, parsed.tag);
            }

            default:
                throw new GrootException("I am Groot. (Invalid command: " + input + ")");
        }
    }

    private static IndexAndTag parseIndexAndTag(Scanner sc, String commandName, String tagAction)
            throws GrootException {
        if (!sc.hasNextInt()) {
            throw new GrootException("Please give a valid index to " + commandName + "!");
        }
        int index = sc.nextInt() - 1;
        String tag = sc.nextLine().trim();
        if (tag.isBlank()) {
            throw new GrootException("Please specify a tag to " + tagAction + " (e.g., " + commandName + " 1 fun)");
        }
        return new IndexAndTag(index, tag);
    }

    private static class IndexAndTag {
        final int index;
        final String tag;

        IndexAndTag(int index, String tag) {
            this.index = index;
            this.tag = tag;
        }
    }
}