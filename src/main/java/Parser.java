import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static boolean parse(String input, TaskList tasks, Ui ui, Storage storage) throws GrootException {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case "bye":
                ui.showMessage("Bye. Hope to see you again soon!");
                return true;
            case "list":
                ui.showMessage("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    ui.showMessage((i + 1) + "." + tasks.get(i));
                }
                break;
            case "todo":
                handleTodo(input, tasks, ui);
                break;
            case "deadline":
                handleDeadline(input, tasks, ui);
                break;
            case "event":
                handleEvent(input, tasks, ui);
                break;
            case "mark":
            case "unmark":
                handleMark(input, tasks, ui, command.equals("mark"));
                break;
            case "delete":
                handleDelete(input, tasks, ui);
                break;
            default:
                throw new GrootException("I am Groot? (I don't know what that means)");
        }
        storage.save(tasks.getAll());
        return false;
    }

    private static void handleTodo(String input, TaskList tasks, Ui ui) throws GrootException {
        if (input.length() <= 5) throw new GrootException("Todo description is empty!");
        Task t = new Todo(input.substring(5).trim());
        tasks.add(t);
        ui.showMessage("Added: " + t + "\n Now you have " + tasks.size() + " tasks.");
    }

    private static void handleDeadline(String input, TaskList tasks, Ui ui) throws GrootException {
        try {
            String[] parts = input.substring(9).split(" /by ");
            LocalDate date = LocalDate.parse(parts[1].trim());
            Task t = new Deadline(parts[0].trim(), date);
            tasks.add(t);
            ui.showMessage("Added: " + t);
        } catch (Exception e) {
            throw new GrootException("Use: deadline <desc> /by yyyy-mm-dd");
        }
    }

    private static void handleEvent(String input, TaskList tasks, Ui ui) throws GrootException {
        try {
            String[] parts = input.substring(6).split(" /from | /to ");
            Task t = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            tasks.add(t);
            ui.showMessage("Added: " + t);
        } catch (Exception e) {
            throw new GrootException("Use: event <desc> /from <start> /to <end>");
        }
    }

    private static void handleMark(String input, TaskList tasks, Ui ui, boolean isDone) throws GrootException {
        int idx = Integer.parseInt(input.split(" ")[1]) - 1;
        Task t = tasks.get(idx);
        if (isDone) t.markAsDone(); else t.markAsNotDone();
        ui.showMessage("Updated:\n   " + t);
    }

    private static void handleDelete(String input, TaskList tasks, Ui ui) throws GrootException {
        int idx = Integer.parseInt(input.split(" ")[1]) - 1;
        Task t = tasks.remove(idx);
        ui.showMessage("Pruned: " + t + "\n Now you have " + tasks.size() + " tasks.");
    }
}