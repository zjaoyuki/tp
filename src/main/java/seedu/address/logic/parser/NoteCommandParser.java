package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

public class NoteCommandParser implements Parser<NoteCommand> {

    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NoteCommand.MESSAGE_USAGE), e);
        }

        String note = argMultiMap.getValue(PREFIX_NOTE).orElse("");

        return new NoteCommand(index, new Note(note));
    }
}
