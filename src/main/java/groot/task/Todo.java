package groot.task;

public class Todo extends Task {

    public Todo(String description) {

        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + statusIcon() + "] " + description;
    }

    @Override
    public String serialize() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
