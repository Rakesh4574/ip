import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Storage {
    private final File file;

    public Storage(String path) {
        this.file = new File(path);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
    }

    public void save(ArrayList<Task> tasks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task t : tasks) {
                pw.println(t.serialize());
            }
        } catch (IOException ignored) {
        }
    }

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