package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphabetic characters, spaces, hyphens, and apostrophes, "
            + "and it should not be blank";

    /*
     * The name must contain only letters, spaces, hyphens, and apostrophes.
     * It should not be empty after trimming.
     */
    public static final String VALIDATION_REGEX = "^[\\p{L}\\s\\-']+$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String normalizedName = normalizeName(name);
        checkArgument(isValidName(normalizedName), MESSAGE_CONSTRAINTS);
        fullName = normalizedName;
    }

    /**
     * Normalizes the name by trimming spaces and collapsing multiple spaces into one.
     */
    private static String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return !test.trim().isEmpty() && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the normalized name for case-insensitive comparison.
     */
    public String getNormalizedName() {
        return fullName.toLowerCase();
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return getNormalizedName().equals(otherName.getNormalizedName());
    }

    @Override
    public int hashCode() {
        return getNormalizedName().hashCode();
    }

}
