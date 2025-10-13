package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name and phone, all other attributes different -> returns true (duplicate)
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withCategory(VALID_CATEGORY_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, same phone, all other attributes same -> returns false (not duplicate)
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, different phone, all other attributes same -> returns false (not duplicate)
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // Case-insensitive name comparison tests
        Person person1 = new PersonBuilder().withName("John Doe").withPhone("98765432").build();
        Person person2 = new PersonBuilder().withName("john doe").withPhone("98765432").build();
        Person person3 = new PersonBuilder().withName("JOHN DOE").withPhone("98765432").build();
        Person person4 = new PersonBuilder().withName("John DOE").withPhone("98765432").build();

        // All variations should be considered the same person (case-insensitive)
        assertTrue(person1.isSamePerson(person2));
        assertTrue(person1.isSamePerson(person3));
        assertTrue(person1.isSamePerson(person4));
        assertTrue(person2.isSamePerson(person3));
        assertTrue(person2.isSamePerson(person4));
        assertTrue(person3.isSamePerson(person4));

        // Same name (case-insensitive) but different phone -> not the same person
        Person person5 = new PersonBuilder().withName("john doe").withPhone("91234567").build();
        assertFalse(person1.isSamePerson(person5));
        assertFalse(person2.isSamePerson(person5));

        // Different name but same phone -> not the same person
        Person person6 = new PersonBuilder().withName("Jane Doe").withPhone("98765432").build();
        assertFalse(person1.isSamePerson(person6));

        // Test with names containing special characters
        Person person7 = new PersonBuilder().withName("Mary-Jane O'Connor").withPhone("98765432").build();
        Person person8 = new PersonBuilder().withName("mary-jane o'connor").withPhone("98765432").build();
        Person person9 = new PersonBuilder().withName("MARY-JANE O'CONNOR").withPhone("98765432").build();

        assertTrue(person7.isSamePerson(person8));
        assertTrue(person7.isSamePerson(person9));
        assertTrue(person8.isSamePerson(person9));

        // Test normalization scenarios
        Person personWithSpaces = new PersonBuilder().withName("John    Doe").withPhone("98765432").build();
        Person personNormalized = new PersonBuilder().withName("John Doe").withPhone("98765432").build();
        // Note: Both should be normalized during construction, so they should be the same
        assertTrue(personWithSpaces.isSamePerson(personNormalized));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different category -> returns false
        editedAlice = new PersonBuilder(ALICE).withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = ALICE.getClass().getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", category=" + ALICE.getCategory()
                + ", note=" + ALICE.getNote()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
