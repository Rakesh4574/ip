package groot.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import groot.GrootException;
import groot.task.Deadline;
import groot.task.Event;
import groot.task.Task;
import groot.task.Todo;

/**
 * Handles loading and saving tasks to a file on the hard disk.
 */
public class Storage {
    private final File file;

    /**
     * Initializes a Storage object. Creates the necessary directory if it doesn't exist.
     * * @param path The file path where task data is stored.
     */
    public Storage(String path) {
        this.file = new File(path);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * Saves the list of tasks to the storage file in a serialized format.
     * * @param tasks The list of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task t : tasks) {
                pw.println(t.serialize());
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not save tasks to file.");
        }
    }

    /**
     * Loads the tasks from the storage file.
     * * @return An ArrayList of Task objects populated from the file.
     * @throws GrootException If the file exists but is corrupted or unreadable.
     */
    public ArrayList<Task> load() throws GrootException {
        ArrayList<Task> loaded = new ArrayList<>();
        if (!file.exists()) {
            return loaded;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(" \\| ");
                Task task = parseTaskFromLine(parts);

                if (task != null) {
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    loadedTasks.add(task);
                }
            }
        } catch (Exception e) {
            throw new GrootException("I am Groot! (The storage file is corrupted and I can't read it.)");
        }
        return loadedTasks;
    }

    /**
     * Parses a single line of the storage file into a Task object.
     * * @param parts The split components of a data line.
     * @return The corresponding Task object, or null if type is unknown.
     */
    private Task parseTaskFromLine(String[] parts) {
        switch (parts[0]) {
            case "T":
                return new Todo(parts[2]);
            case "D":
                return new Deadline(parts[2], LocalDate.parse(parts[3]));
            case "E":
                return new Event(parts[2], parts[3], parts[4]);
            default:
                return null;
        }
    }
}