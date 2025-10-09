package seedu.address.model.person;

/**
 * Represents a Person's note in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notes can contain any text except control characters, up to 500 characters.";

    /*
     * Validates note text input.
     * - Allows: letters, numbers, punctuation, symbols, and spaces
     * - Prevents: control characters that could cause display issues
     * - Maximum length: 500 characters
     */
    public static final String VALIDATION_REGEX = "^[\\p{Print}\\s]*$";
    public static final int MAX_LENGTH = 500;

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param value A valid note.
     */
    public Note(String value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note)) {
            return false;
        }

        Note e = (Note) other;
        return value.equals(e.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
