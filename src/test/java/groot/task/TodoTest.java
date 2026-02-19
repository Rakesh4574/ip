package groot.task;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the functionality of the {@link ToDo} class.
 * Verifies that the string representation and serialization format of Todo tasks
 * behave correctly under different completion statuses, and that tag operations work.
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
     * icon [x] in its string representation.
     */
    @Test
    public void toString_doneTask_correctIcon() {
        ToDo todo = new ToDo("borrow book");
        todo.markAsDone();
        assertEquals("[T][x] borrow book", todo.getStatus());
    }

    /**
     * Verifies that addTag adds a tag and it appears in getStatus and dataInputString.
     */
    @Test
    public void addTag_singleTag_tagAppearsInStatusAndSerialization() {
        ToDo todo = new ToDo("read book");
        todo.addTag("fun");
        assertTrue(todo.hasTag("fun"));
        assertTrue(todo.getStatus().contains("#fun"));
        assertEquals("T | 0 | read book | fun", todo.dataInputString());
    }

    /**
     * Verifies that addTag normalizes tag to lowercase.
     */
    @Test
    public void addTag_uppercaseTag_storedAsLowercase() {
        ToDo todo = new ToDo("task");
        todo.addTag("FUN");
        assertTrue(todo.hasTag("fun"));
        assertTrue(todo.hasTag("FUN"));
    }

    /**
     * Verifies that addTag ignores null and blank tags.
     */
    @Test
    public void addTag_nullOrBlankTag_ignored() {
        ToDo todo = new ToDo("task");
        todo.addTag(null);
        todo.addTag("");
        todo.addTag("   ");
        assertFalse(todo.hasTag(""));
        assertEquals("T | 0 | task", todo.dataInputString());
    }

    /**
     * Verifies that removeTag removes the tag.
     */
    @Test
    public void removeTag_existingTag_tagRemoved() {
        ToDo todo = new ToDo("task");
        todo.addTag("work");
        todo.removeTag("work");
        assertFalse(todo.hasTag("work"));
        assertEquals("T | 0 | task", todo.dataInputString());
    }

    /**
     * Verifies that removeTag with non-existent tag does not throw.
     */
    @Test
    public void removeTag_nonExistentTag_noError() {
        ToDo todo = new ToDo("task");
        todo.removeTag("missing");
        assertEquals("T | 0 | task", todo.dataInputString());
    }

    /**
     * Verifies that a ToDo with tags loaded from storage serializes correctly.
     */
    @Test
    public void serialize_todoWithMultipleTags_correctFormat() {
        Set<String> tags = new HashSet<>();
        tags.add("urgent");
        tags.add("work");
        ToDo todo = new ToDo("submit report", false, tags);
        String data = todo.dataInputString();
        assertTrue(data.contains("submit report"));
        assertTrue(data.contains("urgent") || data.contains("work"));
    }
}