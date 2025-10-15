package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClass(String)}
 */
public class Class {

    public static final String MESSAGE_CONSTRAINTS =
            "Class must be one of the following (case-insensitive): "
            + "1A, 1B, 1C, 1D, 2A, 2B, 2C, 2D, 3A, 3B, 3C, 3D, "
            + "4A, 4B, 4C, 4D, 5A, 5B, 5C, 5D, 6A, 6B, 6C, 6D";

    public static final String VALIDATION_REGEX = "^(?i)[1-6][A-D]$";

    public final String value;

    /**
     * Constructs a {@code Class}.
     *
     * @param studentClass A valid class.
     */
    public Class(String studentClass) {
        requireNonNull(studentClass);
        String trimmedClass = studentClass.trim().toUpperCase();
        checkArgument(isValidClass(trimmedClass), MESSAGE_CONSTRAINTS);
        value = trimmedClass;
    }

    /**
     * Returns true if a given string is a valid class.
     */
    public static boolean isValidClass(String test) {
        return test.trim().matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof Class)) {
            return false;
        }

        Class otherClass = (Class) other;
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
