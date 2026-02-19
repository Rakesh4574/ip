package groot;

import groot.task.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Storage {
    public static final DateTimeFormatter DATE_DATA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String filePath;
    private File file;

    public Storage(String filePath) throws IOException {
        this.filePath = filePath;
        this.file = new File(filePath);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        file.createNewFile();
    }

    private Set<String> parseTags(String[] parts, int tagIndex) {
        if (parts.length <= tagIndex || parts[tagIndex].isBlank()) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(parts[tagIndex].split(",")));
    }

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
                    Set<String> todoTags = parseTags(parts, 3);
                    listOfTasks.add(new ToDo(name, isDone, todoTags));
                    break;
                case "D":
                    LocalDateTime by = LocalDate.parse(parts[3], DATE_DATA_FORMATTER).atStartOfDay();
                    Set<String> deadlineTags = parseTags(parts, 4);
                    listOfTasks.add(new Deadline(name, by, isDone, deadlineTags));
                    break;
                case "E":
                    LocalDateTime from = LocalDate.parse(parts[3], DATE_DATA_FORMATTER).atStartOfDay();
                    LocalDateTime to = LocalDate.parse(parts[4], DATE_DATA_FORMATTER).atStartOfDay();
                    Set<String> eventTags = parseTags(parts, 5);
                    listOfTasks.add(new Event(name, from, to, isDone, eventTags));
                    break;
            }
        }
        return listOfTasks;
    }

    public void updateDataFile(TaskList taskList) throws IOException {
        ArrayList<Task> tasks = taskList.get();
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.dataInputString());
        }
        Files.write(Paths.get(filePath), lines);
    }
}