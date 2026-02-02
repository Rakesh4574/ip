package groot;
import groot.task.TaskList;
import groot.ui.Ui;
import groot.storage.Storage;

public class Groot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Groot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (GrootException e) {
            ui.showError("Data file not found. Starting with empty list.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                isExit = Parser.parse(fullCommand, tasks, ui, storage);
            } catch (GrootException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Groot("data/groot.txt").run();
    }
}