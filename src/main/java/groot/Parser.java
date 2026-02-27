package groot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
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
    public static final DateTimeFormatter DATE_DATA_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

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
                LocalDateTime dateTime = parseDate(nameAndBy[1]);
                return new AddCommand(nameAndBy[0].trim(), dateTime);

            case "event":
                String eventLine = sc.nextLine();
                String[] nameAndFrom = eventLine.split(" /from ");
                if (nameAndFrom.length != 2) throw new GrootException("Use: <Desc> /from <date> /to <date>");
                String[] fromAndTo = nameAndFrom[1].split(" /to ");
                if (fromAndTo.length != 2) throw new GrootException("Missing /to date!");
                LocalDateTime fromDt = parseDate(fromAndTo[0]);
                LocalDateTime toDt = parseDate(fromAndTo[1]);
                if (toDt.isBefore(fromDt)) {
                    throw new GrootException("End date/time cannot be before the start date/time!");
                }
                return new AddCommand(nameAndFrom[0].trim(), fromDt, toDt);

            case "find":
                return new FindCommand(sc.nextLine().trim());

            case "tag":
                if (!sc.hasNextInt()) throw new GrootException("Please give a valid index to tag!");
                int tagIdx = sc.nextInt() - 1;
                String tagLine = sc.hasNextLine() ? sc.nextLine().trim() : "";
                List<String> tagTokens = new ArrayList<>();
                if (!tagLine.isBlank()) {
                    for (String token : tagLine.split("\\s+")) {
                        if (!token.isBlank()) {
                            tagTokens.add(token);
                        }
                    }
                }
                if (tagTokens.isEmpty()) {
                    throw new GrootException("Please specify at least one tag (e.g., tag 1 fun)");
                }
                return new TagCommand(tagIdx, tagTokens);

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

    private static LocalDateTime parseDate(String rawDate) throws GrootException {
        String trimmed = rawDate == null ? "" : rawDate.trim();
        try {
            return LocalDate.parse(trimmed, DATE_DATA_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new GrootException(String.format("Invalid date '%s'. Use yyyy-MM-dd (e.g., 2026-02-10)", trimmed));
        }
    }
}
