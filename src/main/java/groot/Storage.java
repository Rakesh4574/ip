package groot;

import groot.task.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles persistence of Groot's task list to a plain-text data file.
 */
public class Storage {
    /** Formatter used for the yyyy-MM-dd representation saved on disk. */
    public static final DateTimeFormatter DATE_DATA_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private final String filePath;
    private File file;

    /**
     * Constructs a Storage helper that ensures the file exists before use.
     *
     * @param filePath The path to the Groot data file.
     * @throws IOException If the file cannot be created.
     */
    public Storage(String filePath) throws IOException {
        this.filePath = filePath;
        this.file = new File(filePath);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        file.createNewFile();
    }

    /**
     * Parses the comma-separated tag section of a stored line.
     *
     * @param parts    Split line segments.
     * @param tagIndex Index where tags are expected.
     * @return A set of normalized tags or an empty set if none are present.
     */
    private Set<String> parseTags(String[] parts, int tagIndex) {
        if (tagIndex < 0 || parts.length <= tagIndex || parts[tagIndex].isBlank()) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(parts[tagIndex].split(",")));
    }

    /**
     * Loads persisted tasks from disk, recreating the appropriate Task subclasses.
     *
     * @return The list of tasks read from storage.
     * @throws Exception On any parsing or IO failure.
     */
    public ArrayList<Task> loadTasks() throws Exception {
        ArrayList<Task> listOfTasks = new ArrayList<>();
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String[] parts = sc.nextLine().split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String name = parts[2];

            switch (type) {
                case "T":
                    int todoTagIndex = parts.length > 3 ? 3 : -1;
                    Set<String> todoTags = parseTags(parts, todoTagIndex);
                    listOfTasks.add(new ToDo(name, isDone, todoTags));
                    break;
                case "D":
                    String dateValue = parts[parts.length - 1];
                    int deadlineTagIndex = parts.length > 4 ? parts.length - 2 : -1;
                    Set<String> deadlineTags = parseTags(parts, deadlineTagIndex);
                    LocalDateTime by = LocalDate.parse(dateValue, DATE_DATA_FORMATTER).atStartOfDay();
                    listOfTasks.add(new Deadline(name, by, isDone, deadlineTags));
                    break;
                case "E":
                    String toValue = parts[parts.length - 1];
                    String fromValue = parts[parts.length - 2];
                    int eventTagIndex = parts.length > 5 ? parts.length - 3 : -1;
                    Set<String> eventTags = parseTags(parts, eventTagIndex);
                    LocalDateTime from = LocalDate.parse(fromValue, DATE_DATA_FORMATTER).atStartOfDay();
                    LocalDateTime to = LocalDate.parse(toValue, DATE_DATA_FORMATTER).atStartOfDay();
                    listOfTasks.add(new Event(name, from, to, isDone, eventTags));
                    break;
            }
        }
        return listOfTasks;
    }

    /**
     * Writes the current task list back to the data file so it can be reloaded later.
     *
     * @param taskList The in-memory task collection to persist.
     * @throws IOException If writing fails.
     */
    public void updateDataFile(TaskList taskList) throws IOException {
        ArrayList<Task> tasks = taskList.get();
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.dataInputString());
        }
        Files.write(Paths.get(filePath), lines);
    }
}
