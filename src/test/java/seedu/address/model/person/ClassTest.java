package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClassTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Class(null));
    }

    @Test
    public void constructor_invalidClass_throwsIllegalArgumentException() {
        String invalidClass = "7E";
        assertThrows(IllegalArgumentException.class, () -> new Class(invalidClass));
    }

    @Test
    public void constructor_classNormalization_success() {
        // Mixed case should be normalized to uppercase
        Class studentClass = new Class("k1a");
        assertEquals("K1A", studentClass.value);

        studentClass = new Class("k2b");
        assertEquals("K2B", studentClass.value);

        studentClass = new Class("nursery");
        assertEquals("NURSERY", studentClass.value);

        studentClass = new Class("pre-k");
        assertEquals("PRE-K", studentClass.value);

        // Leading and trailing spaces should be trimmed
        studentClass = new Class("  K1A  ");
        assertEquals("K1A", studentClass.value);

        studentClass = new Class("  nursery  ");
        assertEquals("NURSERY", studentClass.value);
    }

    @Test
    public void isValidClass() {
        // null class
        assertThrows(NullPointerException.class, () -> Class.isValidClass(null));

        // invalid classes
        assertFalse(Class.isValidClass("")); // empty string
        assertFalse(Class.isValidClass(" ")); // spaces only
        assertFalse(Class.isValidClass("K3A")); // invalid kindergarten level (K3)
        assertFalse(Class.isValidClass("K0A")); // invalid kindergarten level (K0)
        assertFalse(Class.isValidClass("K1D")); // invalid section (D)
        assertFalse(Class.isValidClass("K1A1")); // extra character
        assertFalse(Class.isValidClass("teacher")); // word instead of class format
        assertFalse(Class.isValidClass("1A")); // old primary school format
        assertFalse(Class.isValidClass("Grade1")); // invalid format
        assertFalse(Class.isValidClass("Reception")); // removed class type
        assertFalse(Class.isValidClass("Foundation")); // removed class type

        // valid kindergarten classes
        assertTrue(Class.isValidClass("Nursery"));
        assertTrue(Class.isValidClass("Pre-K"));
        assertTrue(Class.isValidClass("K1A"));
        assertTrue(Class.isValidClass("K1B"));
        assertTrue(Class.isValidClass("K1C"));
        assertTrue(Class.isValidClass("K2A"));
        assertTrue(Class.isValidClass("K2B"));
        assertTrue(Class.isValidClass("K2C"));

        // case insensitive
        assertTrue(Class.isValidClass("nursery"));
        assertTrue(Class.isValidClass("PRE-K"));
        assertTrue(Class.isValidClass("k1a"));
        assertTrue(Class.isValidClass("K2c"));
    }

    @Test
    public void equals() {
        Class studentClass = new Class("K1A");

        // same values -> returns true
        assertTrue(studentClass.equals(new Class("K1A")));

        // same object -> returns true
        assertTrue(studentClass.equals(studentClass));

        // null -> returns false
        assertFalse(studentClass.equals(null));

        // different types -> returns false
        assertFalse(studentClass.equals(5.0f));

        // different values -> returns false
        assertFalse(studentClass.equals(new Class("K2B")));
    }

    @Test
    public void hashCode_sameClass_sameHashCode() {
        Class class1 = new Class("K1A");
        Class class2 = new Class("K1A");
        assertEquals(class1.hashCode(), class2.hashCode());
    }

    @Test
    public void toString_validClass_returnsCorrectString() {
        Class studentClass = new Class("K2C");
        assertEquals("K2C", studentClass.toString());
    }
}
