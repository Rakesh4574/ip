package groot.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void testAddTask() {
        taskList.get().add(new ToDo("read book"));
        assertEquals(1, taskList.get().size());
    }

    @Test
    public void testSize() {
        assertEquals(0, taskList.get().size());
    }

    /**
     * Verifies that findTask returns tasks whose name contains the keyword (case-insensitive).
     */
    @Test
    public void findTask_matchingKeyword_returnsMatchingTasks() {
        taskList.get().add(new ToDo("Read book"));
        taskList.get().add(new ToDo("Buy groceries"));
        taskList.get().add(new ToDo("Read newspaper"));

        ArrayList<Task> found = taskList.findTask("read");
        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(t -> t.getStatus().contains("Read book")));
        assertTrue(found.stream().anyMatch(t -> t.getStatus().contains("Read newspaper")));
    }

    /**
     * Verifies that findTask is case-insensitive.
     */
    @Test
    public void findTask_uppercaseKeyword_findsLowercaseMatch() {
        taskList.get().add(new ToDo("read book"));
        ArrayList<Task> found = taskList.findTask("READ");
        assertEquals(1, found.size());
        assertTrue(found.get(0).getStatus().contains("read book"));
    }

    /**
     * Verifies that findTask returns empty list when no match.
     */
    @Test
    public void findTask_noMatch_returnsEmptyList() {
        taskList.get().add(new ToDo("read book"));
        ArrayList<Task> found = taskList.findTask("xyz");
        assertTrue(found.isEmpty());
    }

    /**
     * Verifies that findTaskByTag returns only tasks with the given tag.
     */
    @Test
    public void findTaskByTag_matchingTag_returnsTaggedTasks() {
        ToDo t1 = new ToDo("task one");
        t1.addTag("work");
        ToDo t2 = new ToDo("task two");
        t2.addTag("fun");
        ToDo t3 = new ToDo("task three");
        t3.addTag("work");

        taskList.get().add(t1);
        taskList.get().add(t2);
        taskList.get().add(t3);

        ArrayList<Task> found = taskList.findTaskByTag("work");
        assertEquals(2, found.size());
        assertTrue(found.stream().allMatch(t -> t.hasTag("work")));
    }

    /**
     * Verifies that findTaskByTag returns empty list when no task has the tag.
     */
    @Test
    public void findTaskByTag_noMatch_returnsEmptyList() {
        ToDo todo = new ToDo("task");
        todo.addTag("fun");
        taskList.get().add(todo);

        ArrayList<Task> found = taskList.findTaskByTag("work");
        assertTrue(found.isEmpty());
    }
}