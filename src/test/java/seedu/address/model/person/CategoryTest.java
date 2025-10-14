package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "teacher";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    public void constructor_categoryNormalization_success() {
        // Mixed case should be normalized to lowercase
        Category category = new Category("Student");
        assertEquals("student", category.value);

        category = new Category("STUDENT");
        assertEquals("student", category.value);

        category = new Category("Colleague");
        assertEquals("colleague", category.value);

        category = new Category("COLLEAGUE");
        assertEquals("colleague", category.value);

        // Leading and trailing spaces should be trimmed
        category = new Category("  student  ");
        assertEquals("student", category.value);

        category = new Category("  COLLEAGUE  ");
        assertEquals("colleague", category.value);
    }

    @Test
    public void isValidCategory() {
        // null category
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid categories
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("teacher")); // not allowed
        assertFalse(Category.isValidCategory("professor")); // not allowed
        assertFalse(Category.isValidCategory("staff")); // not allowed
        assertFalse(Category.isValidCategory("admin")); // not allowed
        assertFalse(Category.isValidCategory("parent")); // not allowed
        assertFalse(Category.isValidCategory("friend")); // not allowed
        assertFalse(Category.isValidCategory("family")); // not allowed
        assertFalse(Category.isValidCategory("other")); // not allowed
        assertFalse(Category.isValidCategory("students")); // plural not allowed
        assertFalse(Category.isValidCategory("colleagues")); // plural not allowed

        // valid categories - exact match
        assertTrue(Category.isValidCategory("student"));
        assertTrue(Category.isValidCategory("colleague"));

        // valid categories - case insensitive
        assertTrue(Category.isValidCategory("Student"));
        assertTrue(Category.isValidCategory("STUDENT"));
        assertTrue(Category.isValidCategory("Colleague"));
        assertTrue(Category.isValidCategory("COLLEAGUE"));
        assertTrue(Category.isValidCategory("StUdEnT")); // mixed case
        assertTrue(Category.isValidCategory("CoLlEaGuE")); // mixed case

        // valid categories with whitespace (should be trimmed)
        assertTrue(Category.isValidCategory(" student "));
        assertTrue(Category.isValidCategory("  colleague  "));
        assertTrue(Category.isValidCategory("\tstudent\t")); // tabs
        assertTrue(Category.isValidCategory("\ncolleague\n")); // newlines
    }

    @Test
    public void equals() {
        Category category = new Category("student");

        // same values -> returns true
        assertTrue(category.equals(new Category("student")));

        // same object -> returns true
        assertTrue(category.equals(category));

        // null -> returns false
        assertFalse(category.equals(null));

        // different type -> returns false
        assertFalse(category.equals(5.0f));

        // different values -> returns false
        assertFalse(category.equals(new Category("colleague")));

        // normalized values should be equal (case insensitive after normalization)
        assertTrue(category.equals(new Category("STUDENT")));
        assertTrue(category.equals(new Category("Student")));
        assertTrue(category.equals(new Category(" student ")));
    }

    @Test
    public void toString_returnsNormalizedValue() {
        Category category = new Category("student");
        assertEquals("student", category.toString());

        // Test with mixed case - should be normalized
        Category categoryMixedCase = new Category("Student");
        assertEquals("student", categoryMixedCase.toString());

        Category categoryUpperCase = new Category("COLLEAGUE");
        assertEquals("colleague", categoryUpperCase.toString());

        // Test with whitespace - should be trimmed and normalized
        Category categoryWithSpaces = new Category("  Student  ");
        assertEquals("student", categoryWithSpaces.toString());
    }

    @Test
    public void hashCode_consistency() {
        Category category1 = new Category("student");
        Category category2 = new Category("student");
        Category category3 = new Category("colleague");

        // Same categories should have same hash code
        assertEquals(category1.hashCode(), category2.hashCode());

        // Normalized categories should have same hash code
        Category categoryMixedCase = new Category("Student");
        assertEquals(category1.hashCode(), categoryMixedCase.hashCode());

        // Different categories may have different hash codes
        // We just test that hashCode() doesn't throw exceptions
        category3.hashCode();
    }
}
