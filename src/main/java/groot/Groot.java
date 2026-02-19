package groot;

import command.Command;
import groot.task.TaskList;
import groot.Ui;
import groot.Storage;
import groot.Parser;

/**
 * Entry point for the Groot task manager that orchestrates parsing, execution, and storage.
 */
public class Groot {
    private static final String FILEPATH = "./data/groot.txt";
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private String commandType = "";

    /**
     * Initializes Groot with UI helpers, storage, and the persisted task list.
     */
    public Groot() {
        try {
            this.ui = new Ui();
            this.storage = new Storage(FILEPATH);
            this.tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            System.err.println("Initialization failed: " + e.getMessage());
        }
    }

    /**
     * Generates Groot's response to a raw user input string.
     *
     * @param input The line entered by the user.
     * @return The full response text to display.
     */
    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return ui.printByeMessage();
        }
        try {
            Command c = Parser.parse(input);
            String result = c.execute(tasks, ui, storage);
            commandType = c.getClass().getSimpleName();
            return result;
        } catch (Exception e) {
            commandType = "Error";
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Returns the simple name of the command most recently executed.
     *
     * @return The last command type or "Error" on failure.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Provides the welcome message shown when the UI launches.
     *
     * @return The welcome text.
     */
    public String getWelcomeMessage() {
        return ui.printWelcomeMessage();
    }
}
