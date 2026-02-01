import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath = "data/groot.txt";

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return tasks; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            try (FileWriter fw = new FileWriter(filePath)) {
                for (Task t : tasks) {
                    fw.write(t.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\|");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        switch (type) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type");
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
