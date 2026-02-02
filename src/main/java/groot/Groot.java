package groot;

import groot.task.TaskList;
import groot.ui.Ui;
import groot.storage.Storage;

/**
 * The main class for the Groot task management application.
 * Groot is a CLI-based assistant that helps users track todos, deadlines, and events.
 */
public class Groot {
    /** Handles loading from and saving tasks to the hard disk. */
    private Storage storage;

    /** The internal list used to manage tasks during runtime. */
    private TaskList tasks;

    /** Handles all interactions with the user (input and output). */
    private Ui ui;

    /**
     * Initializes a new Groot instance.
     * Sets up the user interface and storage, and attempts to load existing tasks
     * from the specified file path.
     *
     * @param filePath The path to the file where task data is persisted.
     */
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

    /**
     * Starts the main execution loop of the application.
     * Displays the welcome message and continuously processes user commands
     * until an exit command is received.
     */
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

    /**
     * The main entry point for the application.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Groot("data/groot.txt").run();
    }
}