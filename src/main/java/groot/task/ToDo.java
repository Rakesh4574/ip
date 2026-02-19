package groot.task;

import java.util.Set;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name, 'T');
    }

    public ToDo(String name, boolean isTaskDone) {
        super(name, 'T', isTaskDone);
    }

    public ToDo(String name, boolean isTaskDone, Set<String> tags) {
        super(name, 'T', isTaskDone, tags);
    }
}