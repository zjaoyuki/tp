package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithNote_returnsNoteCommand() {
        String userInput = "1 " + PREFIX_NOTE + "Allergic to peanuts";
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note("Allergic to peanuts"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validArgsWithEmptyNote_returnsNoteCommand() {
        String userInput = "1 " + PREFIX_NOTE;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingNotePrefix_returnsNoteCommandWithEmptyNote() throws Exception {
        String userInput = "1";
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
