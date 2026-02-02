package groot.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the functionality of the {@link Todo} class.
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
        Todo todo = new Todo("borrow book");
        assertEquals("T | 0 | borrow book", todo.serialize());
    }

    /**
     * Verifies that a completed Todo task displays the correct completion
     * icon [X] in its string representation.
     */
    @Test
    public void toString_doneTask_correctIcon() {
        Todo todo = new Todo("borrow book");
        todo.markAsDone();
        assertEquals("[T][X] borrow book", todo.toString());
    }
}