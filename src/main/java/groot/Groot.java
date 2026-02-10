package groot;

import command.Command;
import groot.task.TaskList;
import groot.Ui;
import groot.Storage;
import groot.Parser;

public class Groot {
    private static final String FILEPATH = "./data/groot.txt";
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private String commandType = "";

    public Groot() {
        try {
            this.ui = new Ui();
            this.storage = new Storage(FILEPATH);
            this.tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            System.err.println("Initialization failed: " + e.getMessage());
        }
    }

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

    public String getCommandType() {
        return commandType;
    }

    public String getWelcomeMessage() {
        return ui.printWelcomeMessage();
    }
}