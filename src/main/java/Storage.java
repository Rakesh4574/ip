import java.io.*;
import java.time.LocalDate;
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
                Task t = null;

                switch (p[0]) {
                    case "T":
                        t = new Todo(p[2]);
                        break;
                    case "D":
                        LocalDate date = LocalDate.parse(p[3]);
                        t = new Deadline(p[2], date);
                        break;
                    case "E":
                        t = new Event(p[2], p[3], p[4]);
                        break;
                    default:
                        continue;
                }

                if (p[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException ignored) {
        }
    }
}
