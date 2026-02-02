package groot.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void serialize_notDoneTask_correctFormat() {
        Todo todo = new Todo("borrow book");
        assertEquals("T | 0 | borrow book", todo.serialize());
    }

    @Test
    public void toString_doneTask_correctIcon() {
        Todo todo = new Todo("borrow book");
        todo.markAsDone();
        assertEquals("[T][X] borrow book", todo.toString());
    }
}