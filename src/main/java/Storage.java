import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final File file;

    public Storage(String path) {
        this.file = new File(path);
        file.getParentFile().mkdirs();
    }

    public void save(ArrayList<Task> tasks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task t : tasks) {
                pw.println(t.serialize());
            }
        } catch (IOException ignored) {
        }
    }

    public void load(ArrayList<Task> tasks) {
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(" \\| ");
                Task t = switch (p[0]) {
                    case "T" -> new Todo(p[2]);
                    case "D" -> new Deadline(p[2], p[3]);
                    case "E" -> new Event(p[2], p[3], p[4]);
                    default -> null;
                };
                if (t != null && p[1].equals("1")) t.markAsDone();
                if (t != null) tasks.add(t);
            }
        } catch (IOException ignored) {
        }
    }
}
