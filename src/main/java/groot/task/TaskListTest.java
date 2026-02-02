package groot.task;

import groot.GrootException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    @Test
    public void add_todoTask_sizeIncreases() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void remove_invalidIndex_exceptionThrown() {
        try {
            new TaskList().remove(10);
            fail();
        } catch (GrootException e) {
            assertEquals("Invalid task number.", e.getMessage());
        }
    }
}