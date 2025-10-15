package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_validData_returnsCorrectPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check that we have the expected number of sample persons
        assertEquals(6, samplePersons.length);

        // Check that all persons have valid data
        for (Person person : samplePersons) {
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getStudentClass());
            assertNotNull(person.getNote());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_validClasses_returnsCorrectClasses() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        List<Person> personList = Arrays.asList(samplePersons);

        // Count persons by class
        long class1ACount = personList.stream()
                .filter(person -> "1A".equals(person.getStudentClass().value))
                .count();
        long class2BCount = personList.stream()
                .filter(person -> "2B".equals(person.getStudentClass().value))
                .count();
        long class3CCount = personList.stream()
                .filter(person -> "3C".equals(person.getStudentClass().value))
                .count();
        long class4DCount = personList.stream()
                .filter(person -> "4D".equals(person.getStudentClass().value))
                .count();
        long class5ACount = personList.stream()
                .filter(person -> "5A".equals(person.getStudentClass().value))
                .count();
        long class6BCount = personList.stream()
                .filter(person -> "6B".equals(person.getStudentClass().value))
                .count();

        // Verify that we have a good distribution of classes
        assertTrue(class1ACount > 0 || class2BCount > 0 || class3CCount > 0 ||
                   class4DCount > 0 || class5ACount > 0 || class6BCount > 0);
    }

    @Test
    public void getSamplePersons_specificPersons_haveCorrectClasses() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Test specific persons based on their expected classes
        for (int i = 0; i < samplePersons.length; i++) {
            Person person = samplePersons[i];
            switch (i) {
                case 0:
                    assertEquals("1A", person.getStudentClass().value);
                    break;
                case 1:
                    assertEquals("2B", person.getStudentClass().value);
                    break;
                case 2:
                    assertEquals("3C", person.getStudentClass().value);
                    break;
                case 3:
                    assertEquals("4D", person.getStudentClass().value);
                    break;
                case 4:
                    assertEquals("5A", person.getStudentClass().value);
                    break;
                case 5:
                    assertEquals("6B", person.getStudentClass().value);
                    break;
            }
        }
    }
}
