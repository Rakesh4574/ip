package groot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import groot.GrootException;

/**
 * Provides unit tests for the {@link TaskList} class.
 * Verifies that tasks can be added correctly and that edge cases, such as
 * invalid index access, are handled via exceptions.
 */
public class TaskListTest {

    /**
     * Tests that adding a Todo task correctly increments the size of the TaskList.
     */
    @Test
    public void add_todoTask_sizeIncreases() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        assertEquals(1, tasks.size());
    }

    /**
     * Tests that attempting to remove a task using an invalid index throws
     * a {@link GrootException} with the correct error message.
     */
    @Test
    public void remove_invalidIndex_exceptionThrown() {
        try {
            new TaskList().remove(10);
            fail("Expected GrootException was not thrown");
        } catch (GrootException e) {
            assertEquals("Invalid task number. I can't prune what isn't there!", e.getMessage());
        }
    }
}