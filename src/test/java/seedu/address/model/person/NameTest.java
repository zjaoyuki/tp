package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_nameNormalization_success() {
        // Multiple spaces should be collapsed to single space
        Name name = new Name("John    Doe");
        assertEquals("John Doe", name.fullName);

        // Leading and trailing spaces should be trimmed
        name = new Name("  Mary Jane  ");
        assertEquals("Mary Jane", name.fullName);

        // Mixed spacing issues should be handled
        name = new Name("  Peter   Paul   Mary  ");
        assertEquals("Peter Paul Mary", name.fullName);
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("   ")); // multiple spaces only
        assertFalse(Name.isValidName("John123")); // contains numbers
        assertFalse(Name.isValidName("John@Doe")); // contains invalid symbols
        assertFalse(Name.isValidName("John*")); // contains asterisk
        assertFalse(Name.isValidName("John&Doe")); // contains ampersand
        assertFalse(Name.isValidName("John#Doe")); // contains hash
        assertFalse(Name.isValidName("John$Doe")); // contains dollar sign
        assertFalse(Name.isValidName("John%Doe")); // contains percent
        assertFalse(Name.isValidName("John(Doe)")); // contains parentheses
        assertFalse(Name.isValidName("John+Doe")); // contains plus
        assertFalse(Name.isValidName("John=Doe")); // contains equals

        // valid names - alphabetic characters only
        assertTrue(Name.isValidName("John")); // single word
        assertTrue(Name.isValidName("John Doe")); // two words
        assertTrue(Name.isValidName("Mary Jane Smith")); // three words
        assertTrue(Name.isValidName("a")); // single character
        assertTrue(Name.isValidName("Z")); // single uppercase character

        // valid names - with hyphens
        assertTrue(Name.isValidName("Mary-Jane")); // hyphenated first name
        assertTrue(Name.isValidName("Smith-Jones")); // hyphenated last name
        assertTrue(Name.isValidName("Mary-Jane Smith-Jones")); // multiple hyphens
        assertTrue(Name.isValidName("Jean-Claude Van Damme")); // mixed hyphens and spaces

        // valid names - with apostrophes
        assertTrue(Name.isValidName("O'Connor")); // name with apostrophe
        assertTrue(Name.isValidName("D'Angelo")); // name with apostrophe
        assertTrue(Name.isValidName("Mary O'Brien")); // first and last name with apostrophe
        assertTrue(Name.isValidName("Jean-Luc D'Artagnan")); // mixed hyphens and apostrophes

        // valid names - mixed case
        assertTrue(Name.isValidName("john")); // all lowercase
        assertTrue(Name.isValidName("JOHN")); // all uppercase
        assertTrue(Name.isValidName("John")); // proper case
        assertTrue(Name.isValidName("mCdonald")); // mixed case

        // valid names - complex combinations
        assertTrue(Name.isValidName("Mary-Jane O'Connor Smith")); // all special characters
        assertTrue(Name.isValidName("Jean-Baptiste D'Artagnan")); // French-style name
        assertTrue(Name.isValidName("Anna-Maria José-Carlos")); // Spanish-style name
    }

    @Test
    public void getNormalizedName_caseInsensitive_success() {
        Name name1 = new Name("John Doe");
        Name name2 = new Name("JOHN DOE");
        Name name3 = new Name("john doe");
        Name name4 = new Name("John DOE");

        // All variations should have the same normalized name
        assertEquals("john doe", name1.getNormalizedName());
        assertEquals("john doe", name2.getNormalizedName());
        assertEquals("john doe", name3.getNormalizedName());
        assertEquals("john doe", name4.getNormalizedName());

        // Test with special characters
        Name name5 = new Name("Mary-Jane O'Connor");
        Name name6 = new Name("MARY-JANE O'CONNOR");
        assertEquals("mary-jane o'connor", name5.getNormalizedName());
        assertEquals("mary-jane o'connor", name6.getNormalizedName());
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different type -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));

        // Note: equals uses case-insensitive comparison
        assertTrue(name.equals(new Name("valid name"))); // same name, different case
        assertTrue(name.equals(new Name("Valid  Name"))); // different spacing gets normalized
    }

    @Test
    public void toString_returnsFullName() {
        Name name = new Name("John Doe");
        assertEquals("John Doe", name.toString());

        // Test with normalized input
        Name nameWithExtraSpaces = new Name("  John   Doe  ");
        assertEquals("John Doe", nameWithExtraSpaces.toString());

        // Test with special characters
        Name nameWithSpecialChars = new Name("Mary-Jane O'Connor");
        assertEquals("Mary-Jane O'Connor", nameWithSpecialChars.toString());
    }

    @Test
    public void hashCode_consistency() {
        Name name1 = new Name("John Doe");
        Name name2 = new Name("John Doe");
        Name name3 = new Name("Jane Doe");

        // Same names should have same hash code
        assertEquals(name1.hashCode(), name2.hashCode());

        // Different names may have different hash codes (not guaranteed, but likely)
        // We just test that hashCode() doesn't throw exceptions
        name3.hashCode();
    }
}
