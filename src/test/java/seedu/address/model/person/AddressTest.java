package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void constructor_validAddress_success() {
        // Valid addresses
        Address address = new Address("123 Main Street");
        assertEquals("123 Main Street", address.value);

        address = new Address("Blk 456, Den Road, #01-355");
        assertEquals("Blk 456, Den Road, #01-355", address.value);

        address = new Address("Leng Inc; 1234 Market St");
        assertEquals("Leng Inc; 1234 Market St", address.value);

        // Address with leading/trailing spaces should be trimmed during parsing
        address = new Address("123 Main Street");
        assertEquals("123 Main Street", address.value);
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // blank address
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("  ")); // multiple spaces only

        // invalid addresses - starting with whitespace
        assertFalse(Address.isValidAddress(" 123 Main Street"));
        assertFalse(Address.isValidAddress("\t123 Main Street"));
        assertFalse(Address.isValidAddress("\n123 Main Street"));

        // valid addresses
        assertTrue(Address.isValidAddress("123 Main Street"));
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St"));
        assertTrue(Address.isValidAddress("a")); // minimal valid address
        assertTrue(Address.isValidAddress("123")); // numbers only
        assertTrue(Address.isValidAddress("Block 123 Ang Mo Kio Avenue 3, #12-34"));
        assertTrue(Address.isValidAddress("311, Clementi Ave 2, #02-25"));
        assertTrue(Address.isValidAddress("Singapore 123456"));
        assertTrue(Address.isValidAddress("Unit #01-01, 123 Main Street, Singapore 654321"));

        // addresses with special characters
        assertTrue(Address.isValidAddress("123 Main St. Apt #4B"));
        assertTrue(Address.isValidAddress("P.O. Box 123"));
        assertTrue(Address.isValidAddress("123-456 Main Street"));
        assertTrue(Address.isValidAddress("123/456 Main Road"));
        assertTrue(Address.isValidAddress("Flat 4B, Block 123, Main Street (West)"));
    }

    @Test
    public void equals() {
        Address address = new Address("123 Main Street");

        // same values -> returns true
        assertTrue(address.equals(new Address("123 Main Street")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("456 Other Street")));
    }

    @Test
    public void hashCode_sameAddress_sameHashCode() {
        Address address1 = new Address("123 Main Street");
        Address address2 = new Address("123 Main Street");

        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    public void hashCode_differentAddress_differentHashCode() {
        Address address1 = new Address("123 Main Street");
        Address address2 = new Address("456 Other Street");

        // While not guaranteed, different addresses should typically have different hash codes
        // This is a probabilistic test - hash codes could theoretically collide
        assertTrue(address1.hashCode() != address2.hashCode());
    }

    @Test
    public void toString_validAddress_returnsAddress() {
        Address address = new Address("123 Main Street");
        assertEquals("123 Main Street", address.toString());

        address = new Address("Blk 456, Den Road, #01-355");
        assertEquals("Blk 456, Den Road, #01-355", address.toString());
    }
}
