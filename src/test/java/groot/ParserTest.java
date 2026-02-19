package groot;

import command.AddCommand;
import command.DeleteCommand;
import command.FindCommand;
import command.ListCommand;
import command.MarkAsDoneCommand;
import command.TagCommand;
import command.UnmarkAsDoneCommand;
import command.Command;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the functionality of the {@link Parser} class.
 * Verifies that parse returns the correct command type for valid inputs
 * and throws GrootException for invalid inputs.
 */
public class ParserTest {

    @Test
    public void parse_list_returnsListCommand() throws Exception {
        Command cmd = Parser.parse("list");
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    public void parse_todo_returnsAddCommandWithToDo() throws Exception {
        Command cmd = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    public void parse_deadline_validFormat_returnsAddCommand() throws Exception {
        Command cmd = Parser.parse("deadline submit report /by 2026-02-20");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    public void parse_event_validFormat_returnsAddCommand() throws Exception {
        Command cmd = Parser.parse("event meeting /from 2026-02-15 /to 2026-02-15");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    public void parse_mark_returnsMarkAsDoneCommand() throws Exception {
        Command cmd = Parser.parse("mark 1");
        assertInstanceOf(MarkAsDoneCommand.class, cmd);
    }

    @Test
    public void parse_unmark_returnsUnmarkAsDoneCommand() throws Exception {
        Command cmd = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkAsDoneCommand.class, cmd);
    }

    @Test
    public void parse_delete_returnsDeleteCommand() throws Exception {
        Command cmd = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    public void parse_find_returnsFindCommand() throws Exception {
        Command cmd = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_tag_returnsTagCommand() throws Exception {
        Command cmd = Parser.parse("tag 1 fun");
        assertInstanceOf(TagCommand.class, cmd);
    }

    @Test
    public void parse_emptyInput_throwsGrootException() {
        GrootException e = assertThrows(GrootException.class, () -> Parser.parse(""));
        assertEquals("I am Groot? (Invalid Command!)", e.getMessage());
    }

    @Test
    public void parse_unknownCommand_throwsGrootException() {
        GrootException e = assertThrows(GrootException.class, () -> Parser.parse("invalid"));
        assertTrue(e.getMessage().contains("Invalid command"));
    }

    @Test
    public void parse_todoEmptyDescription_throwsException() {
        assertThrows(Exception.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_deadlineMissingBy_throwsGrootException() {
        GrootException e = assertThrows(GrootException.class,
                () -> Parser.parse("deadline submit report"));
        assertTrue(e.getMessage().contains("/by"));
    }

    @Test
    public void parse_tagMissingIndex_throwsGrootException() {
        GrootException e = assertThrows(GrootException.class, () -> Parser.parse("tag fun"));
        assertTrue(e.getMessage().toLowerCase().contains("index"));
    }

    @Test
    public void parse_tagMissingTagName_throwsException() {
        assertThrows(Exception.class, () -> Parser.parse("tag 1"));
    }
}
