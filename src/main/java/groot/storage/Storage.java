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
        } catch (IOException ignored) {
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
                Task t = null;
                switch (p[0]) {
                    case "T":
                        t = new Todo(p[2]);
                        break;
                    case "D":
                        t = new Deadline(p[2], LocalDate.parse(p[3]));
                        break;
                    case "E":
                        t = new Event(p[2], p[3], p[4]);
                        break;
                }
                if (t != null) {
                    if (p[1].equals("1")) {
                        t.markAsDone();
                    }
                    loaded.add(t);
                }
            }
        } catch (Exception e) {
            throw new GrootException("Storage corrupt.");
        }
        return loaded;
    }
}