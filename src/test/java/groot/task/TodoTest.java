package groot.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the functionality of the {@link ToDo} class.
 * Verifies that the string representation and serialization format of Todo tasks
 * behave correctly under different completion statuses.
 */
public class TodoTest {

    /**
     * Verifies that a Todo task that is not yet completed serializes to the
     * correct pipe-separated format for file storage.
     */
    @Test
    public void serialize_notDoneTask_correctFormat() {
        ToDo todo = new ToDo("borrow book");
        assertEquals("T | 0 | borrow book", todo.dataInputString());
    }

    /**
     * Verifies that a completed Todo task displays the correct completion
     * icon [X] in its string representation.
     */
    @Test
    public void toString_doneTask_correctIcon() {
        ToDo todo = new ToDo("borrow book");
        todo.markAsDone();
        assertEquals("[T][x] borrow book", todo.getStatus());
    }
}