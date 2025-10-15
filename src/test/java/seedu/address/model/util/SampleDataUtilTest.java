package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Verify that sample persons array is not null and has expected size
        assertNotNull(samplePersons);
        assertEquals(6, samplePersons.length);
    }

    @Test
    public void getSamplePersons_allPersonsValid() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Verify all sample persons are valid (not null)
        for (Person person : samplePersons) {
            assertNotNull(person);
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getCategory());
            assertNotNull(person.getNote());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_containsExpectedCategories() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Count students and colleagues
        long studentCount = 0;
        long colleagueCount = 0;

        for (Person person : samplePersons) {
            if ("student".equals(person.getCategory().value)) {
                studentCount++;
            } else if ("colleague".equals(person.getCategory().value)) {
                colleagueCount++;
            }
        }

        // Verify we have both students and colleagues
        assertTrue(studentCount > 0, "Should have at least one student");
        assertTrue(colleagueCount > 0, "Should have at least one colleague");
        assertEquals(6, studentCount + colleagueCount, "All persons should be either students or colleagues");
    }

    @Test
    public void getSamplePersons_allHaveEmptyNotes() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Verify all sample persons have empty notes (as per current implementation)
        for (Person person : samplePersons) {
            assertEquals("", person.getNote().value,
                "Sample person " + person.getName().fullName + " should have empty note");
        }
    }

    @Test
    public void getSamplePersons_specificPersonsExist() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check for specific expected persons to ensure the method returns correct data
        boolean foundAlex = false;
        boolean foundBernice = false;
        boolean foundCharlotte = false;
        boolean foundDavid = false;
        boolean foundIrfan = false;
        boolean foundRoy = false;

        for (Person person : samplePersons) {
            String name = person.getName().fullName;
            switch (name) {
            case "Alex Yeoh":
                foundAlex = true;
                assertEquals("student", person.getCategory().value);
                assertEquals("87438807", person.getPhone().value);
                break;
            case "Bernice Yu":
                foundBernice = true;
                assertEquals("colleague", person.getCategory().value);
                assertEquals("99272758", person.getPhone().value);
                break;
            case "Charlotte Oliveiro":
                foundCharlotte = true;
                assertEquals("student", person.getCategory().value);
                assertEquals("93210283", person.getPhone().value);
                break;
            case "David Li":
                foundDavid = true;
                assertEquals("colleague", person.getCategory().value);
                assertEquals("91031282", person.getPhone().value);
                break;
            case "Irfan Ibrahim":
                foundIrfan = true;
                assertEquals("student", person.getCategory().value);
                assertEquals("92492021", person.getPhone().value);
                break;
            case "Roy Balakrishnan":
                foundRoy = true;
                assertEquals("colleague", person.getCategory().value);
                assertEquals("92624417", person.getPhone().value);
                break;
            default:
                // Other persons are not checked in this test
                break;
            }
        }

        assertTrue(foundAlex, "Should contain Alex Yeoh");
        assertTrue(foundBernice, "Should contain Bernice Yu");
        assertTrue(foundCharlotte, "Should contain Charlotte Oliveiro");
        assertTrue(foundDavid, "Should contain David Li");
        assertTrue(foundIrfan, "Should contain Irfan Ibrahim");
        assertTrue(foundRoy, "Should contain Roy Balakrishnan");
    }

    @Test
    public void getSampleAddressBook_returnsValidAddressBook() {
        ReadOnlyAddressBook sampleBook = SampleDataUtil.getSampleAddressBook();

        // Verify the address book is created and populated
        assertNotNull(sampleBook);
        assertEquals(6, sampleBook.getPersonList().size());
    }

    @Test
    public void getSampleAddressBook_containsSamePersonsAsSampleArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        ReadOnlyAddressBook sampleBook = SampleDataUtil.getSampleAddressBook();

        // Verify the address book contains the same persons as the sample array
        assertEquals(samplePersons.length, sampleBook.getPersonList().size());

        // Check that all sample persons are in the address book
        for (Person samplePerson : samplePersons) {
            boolean found = sampleBook.getPersonList().stream()
                .anyMatch(bookPerson -> bookPerson.getName().fullName.equals(samplePerson.getName().fullName));
            assertTrue(found, "Address book should contain " + samplePerson.getName().fullName);
        }
    }
}
