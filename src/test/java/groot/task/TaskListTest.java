package groot.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void testAddTask() {
        ArrayList<Task> list = new ArrayList<>();
        TaskList tasks = new TaskList(list);

        tasks.get().add(new ToDo("read book"));

        assertEquals(1, tasks.get().size());
    }

    @Test
    public void testSize() {
        TaskList tasks = new TaskList(new ArrayList<>());
        assertEquals(0, tasks.get().size());
    }
}