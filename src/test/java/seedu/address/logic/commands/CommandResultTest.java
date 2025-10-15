package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CommandResultTest {

    @Test
    public void constructor_withPersonToView_setsFieldsCorrectly() {
        Person person = new PersonBuilder().build();
        CommandResult result = new CommandResult("test", person);

        assertEquals("test", result.getFeedbackToUser());
        assertEquals(person, result.getPersonToView());
        assertTrue(result.isShowPerson());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void isShowPerson() {
        // With person -> returns true
        Person person = new PersonBuilder().build();
        CommandResult withPerson = new CommandResult("test", person);
        assertTrue(withPerson.isShowPerson());

        // Without person -> returns false
        CommandResult withoutPerson = new CommandResult("test");
        assertFalse(withoutPerson.isShowPerson());

        // With null person -> returns false
        CommandResult withNullPerson = new CommandResult("test", null);
        assertFalse(withNullPerson.isShowPerson());
    }

    @Test
    public void getPersonToShow() {
        Person person = new PersonBuilder().build();
        CommandResult result = new CommandResult("test", person);

        assertEquals(person, result.getPersonToView());
    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, null)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, null)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, null).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", personToView=" + commandResult.getPersonToView() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
