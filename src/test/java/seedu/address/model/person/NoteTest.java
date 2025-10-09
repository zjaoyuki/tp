package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isValidNote() {
        // control characters
        String nullChar = "Hello\u0000World"; // Null character
        String bellChar = "Alert!\u0007"; // Bell (makes terminal beep)
        String backspace = "Text\u0008"; // Backspace
        String tab = "Name:\u0009Value"; // Tab
        String escape = "Escape\u001Bsequence"; // Escape character
        String delete = "Delete\u007Fme"; // Delete character

        // invalid note
        assertFalse(Note.isValidNote(nullChar));
        assertFalse(Note.isValidNote(bellChar));
        assertFalse(Note.isValidNote(backspace));
        assertFalse(Note.isValidNote(tab));
        assertFalse(Note.isValidNote(escape));
        assertFalse(Note.isValidNote(delete));

        // valid note
        assertTrue(Note.isValidNote(""));
        assertTrue(Note.isValidNote("Allergic to peanut."));
        assertTrue(Note.isValidNote("   Allergic to peanut."));
        assertTrue(Note.isValidNote("Needs 30 minute break every 1 hour"));
    }

    @Test
    public void equals() {
        Note note = new Note("Valid Note");

        // same values -> returns true
        assertTrue(note.equals(new Note("Valid Note")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Other Valid Name")));
    }
}
