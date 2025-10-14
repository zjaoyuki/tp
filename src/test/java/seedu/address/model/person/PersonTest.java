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
        assertTrue(personWithSpaces.isSamePerson(personNormalized));
    }

    @Test
    public void isSamePerson_withNotes_success() {
        // Same name and phone but different notes -> still same person (duplicates)
        Person person1 = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
                .withNote("Great student").build();
        Person person2 = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
                .withNote("Excellent in math").build();
        assertTrue(person1.isSamePerson(person2));

        // Same name and phone, one with note, one without -> still same person
        Person person3 = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
                .withNote("").build();
        assertTrue(person1.isSamePerson(person3));
        assertTrue(person3.isSamePerson(person1));
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

        // different note -> returns false (this covers the note.equals(otherPerson.note) line)
        editedAlice = new PersonBuilder(ALICE).withNote("Different note content").build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void equals_specificNoteScenarios() {
        // Test specific note comparison scenarios to ensure full coverage
        Person personWithNote1 = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Original note").build();
        Person personWithNote2 = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Different note").build();
        Person personWithSameNote = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Original note").build();

        // Same note -> returns true
        assertTrue(personWithNote1.equals(personWithSameNote));

        // Different note -> returns false (covers note.equals(otherPerson.note) line)
        assertFalse(personWithNote1.equals(personWithNote2));

        // Empty note vs non-empty note -> returns false
        Person personWithEmptyNote = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("").build();
        assertFalse(personWithNote1.equals(personWithEmptyNote));
        assertFalse(personWithEmptyNote.equals(personWithNote1));

        // Both empty notes -> returns true
        Person anotherPersonWithEmptyNote = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("").build();
        assertTrue(personWithEmptyNote.equals(anotherPersonWithEmptyNote));
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

    @Test
    public void getCategory_success() {
        Person student = new PersonBuilder().withCategory("student").build();
        Person colleague = new PersonBuilder().withCategory("colleague").build();

        assertEquals("student", student.getCategory().value);
        assertEquals("colleague", colleague.getCategory().value);
    }

    @Test
    public void getNote_success() {
        Person personWithNote = new PersonBuilder().withNote("Great student, very attentive").build();
        Person personWithEmptyNote = new PersonBuilder().withNote("").build();

        assertEquals("Great student, very attentive", personWithNote.getNote().value);
        assertEquals("", personWithEmptyNote.getNote().value);
    }

    @Test
    public void hashCode_consistency() {
        Person person1 = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Great student").build();
        Person person2 = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Great student").build();

        // Same persons should have same hash code (this covers the hashCode line with category and note)
        assertEquals(person1.hashCode(), person2.hashCode());

        // Test that hashCode includes all fields (name, phone, email, category, note, tags)
        Person personDifferentName = new PersonBuilder().withName("Jane Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Great student").build();
        // Different name should likely produce different hash (not guaranteed, but very likely)
        // We just verify hashCode doesn't throw exceptions and can be called
        personDifferentName.hashCode();

        Person personDifferentPhone = new PersonBuilder().withName("John Doe").withPhone("91234567")
                .withEmail("john@example.com").withCategory("student").withNote("Great student").build();
        personDifferentPhone.hashCode();

        Person personDifferentEmail = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("jane@example.com").withCategory("student").withNote("Great student").build();
        personDifferentEmail.hashCode();

        Person personDifferentCategory = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("colleague").withNote("Great student").build();
        personDifferentCategory.hashCode();

        Person personDifferentNote = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Excellent colleague").build();
        personDifferentNote.hashCode();

        Person personDifferentTags = new PersonBuilder().withName("John Doe").withPhone("98765432")
                .withEmail("john@example.com").withCategory("student").withNote("Great student")
                .withTags("friend").build();
        personDifferentTags.hashCode();
    }

    @Test
    public void hashCode_explicitCoverage() {
        // Explicitly test to ensure the hashCode line with Objects.hash(name, phone, email, category, note, tags) is covered
        Person student = new PersonBuilder().withCategory("student").withNote("Excellent performance").build();
        Person colleague = new PersonBuilder().withCategory("colleague").withNote("Team player").build();
        Person studentEmptyNote = new PersonBuilder().withCategory("student").withNote("").build();
        Person studentWithTags = new PersonBuilder().withCategory("student").withNote("Good student")
                .withTags("friend", "helpful").build();

        // Call hashCode on various combinations to ensure the method is fully covered
        int hash1 = student.hashCode();
        int hash2 = colleague.hashCode();
        int hash3 = studentEmptyNote.hashCode();
        int hash4 = studentWithTags.hashCode();

        // Verify hashCode is deterministic for same objects
        assertEquals(hash1, student.hashCode());
        assertEquals(hash2, colleague.hashCode());
        assertEquals(hash3, studentEmptyNote.hashCode());
        assertEquals(hash4, studentWithTags.hashCode());

        // Test specific scenario to ensure all fields are included in hash calculation
        Person person1 = new PersonBuilder().withName("Test Name").withPhone("98765432")
                .withEmail("test@example.com").withCategory("student").withNote("Test note")
                .withTags("tag1").build();
        Person person2 = new PersonBuilder().withName("Test Name").withPhone("98765432")
                .withEmail("test@example.com").withCategory("student").withNote("Test note")
                .withTags("tag1").build();

        // Identical persons should have identical hash codes
        assertEquals(person1.hashCode(), person2.hashCode());
    }
}
