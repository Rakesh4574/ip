package groot.task;

import java.time.format.DateTimeFormatter;

public class Task {
    public static final DateTimeFormatter DATE_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public static final DateTimeFormatter DATE_DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static int count = 0;
    protected String name;
    protected boolean isDone;
    protected int idx;
    protected char type;

    public Task(String name, char type) {
        this.name = name;
        this.isDone = false;
        this.idx = ++count;
        this.type = type;
    }

    public Task(String name, char type, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
        this.idx = ++count;
        this.type = type;
    }

    public void markAsDone() { this.isDone = true; }
    public void unmarkAsDone() { this.isDone = false; }
    public String printTask() { return String.format("%d. %s", this.idx, getStatus()); }
    public static int totalTask() { return count; }
    public static void reduceTask() { count--; }
    public void reduceIndex() { this.idx--; }
    public String getStatus() { return String.format("[%c][%s] %s", type, (isDone ? "x" : " "), name); }

    public String dataInputString() {
        return type + " | " + (isDone ? "1" : "0") + " | " + name;
    }
}