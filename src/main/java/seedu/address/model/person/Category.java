package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's category in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Category must be either 'student' or 'colleague' (case-insensitive)";

    public static final String VALIDATION_REGEX = "^(?i)(student|colleague)$";

    public final String value;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        String trimmedCategory = category.trim().toLowerCase();
        checkArgument(isValidCategory(trimmedCategory), MESSAGE_CONSTRAINTS);
        value = trimmedCategory;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
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
        if (!(other instanceof Category)) {
            return false;
        }

        Category otherCategory = (Category) other;
        return value.equals(otherCategory.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
