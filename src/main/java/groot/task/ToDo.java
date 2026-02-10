package groot.task;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name, 'T');
    }

    public ToDo(String name, boolean isTaskDone) {
        super(name, 'T', isTaskDone);
    }

    @Override
    public String dataInputString() {
        return super.dataInputString();
    }
}