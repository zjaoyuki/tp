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
        Class studentClass = new Class("1a");
        assertEquals("1A", studentClass.value);

        studentClass = new Class("2b");
        assertEquals("2B", studentClass.value);

        studentClass = new Class("3C");
        assertEquals("3C", studentClass.value);

        studentClass = new Class("6d");
        assertEquals("6D", studentClass.value);

        // Leading and trailing spaces should be trimmed
        studentClass = new Class("  1A  ");
        assertEquals("1A", studentClass.value);

        studentClass = new Class("  6d  ");
        assertEquals("6D", studentClass.value);
    }

    @Test
    public void isValidClass() {
        // null class
        assertThrows(NullPointerException.class, () -> Class.isValidClass(null));

        // invalid classes
        assertFalse(Class.isValidClass("")); // empty string
        assertFalse(Class.isValidClass(" ")); // spaces only
        assertFalse(Class.isValidClass("7A")); // invalid grade (7)
        assertFalse(Class.isValidClass("0A")); // invalid grade (0)
        assertFalse(Class.isValidClass("1E")); // invalid section (E)
        assertFalse(Class.isValidClass("1a1")); // extra character
        assertFalse(Class.isValidClass("teacher")); // word instead of class format
        assertFalse(Class.isValidClass("class1A")); // extra prefix

        // valid classes
        assertTrue(Class.isValidClass("1A"));
        assertTrue(Class.isValidClass("1B"));
        assertTrue(Class.isValidClass("1C"));
        assertTrue(Class.isValidClass("1D"));
        assertTrue(Class.isValidClass("2A"));
        assertTrue(Class.isValidClass("2B"));
        assertTrue(Class.isValidClass("2C"));
        assertTrue(Class.isValidClass("2D"));
        assertTrue(Class.isValidClass("3A"));
        assertTrue(Class.isValidClass("3B"));
        assertTrue(Class.isValidClass("3C"));
        assertTrue(Class.isValidClass("3D"));
        assertTrue(Class.isValidClass("4A"));
        assertTrue(Class.isValidClass("4B"));
        assertTrue(Class.isValidClass("4C"));
        assertTrue(Class.isValidClass("4D"));
        assertTrue(Class.isValidClass("5A"));
        assertTrue(Class.isValidClass("5B"));
        assertTrue(Class.isValidClass("5C"));
        assertTrue(Class.isValidClass("5D"));
        assertTrue(Class.isValidClass("6A"));
        assertTrue(Class.isValidClass("6B"));
        assertTrue(Class.isValidClass("6C"));
        assertTrue(Class.isValidClass("6D"));
        
        // valid classes with different cases
        assertTrue(Class.isValidClass("1a"));
        assertTrue(Class.isValidClass("2b"));
        assertTrue(Class.isValidClass("6d"));
    }

    @Test
    public void equals() {
        Class studentClass = new Class("1A");

        // same values -> returns true
        assertTrue(studentClass.equals(new Class("1A")));

        // same object -> returns true
        assertTrue(studentClass.equals(studentClass));

        // null -> returns false
        assertFalse(studentClass.equals(null));

        // different types -> returns false
        assertFalse(studentClass.equals(5.0f));

        // different values -> returns false
        assertFalse(studentClass.equals(new Class("2B")));
    }

    @Test
    public void hashCode_sameClass_sameHashCode() {
        Class class1 = new Class("1A");
        Class class2 = new Class("1A");
        assertEquals(class1.hashCode(), class2.hashCode());
    }

    @Test
    public void toString_validClass_returnsCorrectString() {
        Class studentClass = new Class("3C");
        assertEquals("3C", studentClass.toString());
    }
}
