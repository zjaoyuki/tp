package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.DeletePopup;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list or his name.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " n/John ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final String targetName;
    private final boolean isDeletedByName;

    /**
     * Creates a DeleteCommand to delete by index.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.isDeletedByName = false;
    }

    /**
     * Creates a DeleteCommand to delete by name.
     */
    public DeleteCommand(String targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
        this.isDeletedByName = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // delete by name
        if (isDeletedByName) {
            List<Person> exactMatches = lastShownList.stream()
                    .filter(p -> p.getName().fullName.equalsIgnoreCase(targetName))
                    .toList();

            if (exactMatches.size() == 1) {
                Person personToDelete = exactMatches.get(0);
                model.deletePerson(personToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                        Messages.format(personToDelete)));
            }

            // same name duplicates or multiple results when partial info is provided
            List<Person> possibleMatches = exactMatches.isEmpty()
                    ? lastShownList.stream()
                    .filter(p -> p.getName().fullName.toLowerCase().contains(targetName.toLowerCase()))
                    .toList()
                    : exactMatches;

            if (possibleMatches.isEmpty()) {
                showNoMatchPopup();
                throw new CommandException("Deletion cancelled.");
            }

            Person selectedPerson = showDeletePopup(possibleMatches);
            model.deletePerson(selectedPerson);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(selectedPerson)));
        }

        // delete by index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Shows a Delete Pop up for the user to select from matchingResults.
     * */
    private Person showDeletePopup(List<Person> matchingResults) throws CommandException {
        DeletePopup deletePopup = new DeletePopup();
        deletePopup.show("Multiple matches found. Type index and ENTER to delete or ESC to cancel:",
                matchingResults);

        if (deletePopup.isConfirmed()) {
            return deletePopup.getSelectedPerson();
        } else {
            throw new CommandException("Deletion cancelled.");
        }
    }

    /**
     * Shows a Delete Pop up to tell the user no match found.
     */
    private void showNoMatchPopup() {
        DeletePopup deletePopup = new DeletePopup();
        deletePopup.show("No matches found. Press ESC to exit.", List.of());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        if (isDeletedByName && otherDeleteCommand.isDeletedByName) {
            return targetName.equalsIgnoreCase(otherDeleteCommand.targetName);
        }

        return !isDeletedByName && targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .add("isDeletedByName", isDeletedByName)
                .toString();
    }
}
