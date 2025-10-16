package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CliSyntaxTest {

    @Test
    public void prefixConstants_haveCorrectValues() {
        // Test that all prefix constants have the expected string values
        assertEquals("n/", CliSyntax.PREFIX_NAME.getPrefix());
        assertEquals("p/", CliSyntax.PREFIX_PHONE.getPrefix());
        assertEquals("e/", CliSyntax.PREFIX_EMAIL.getPrefix());
        assertEquals("a/", CliSyntax.PREFIX_ADDRESS.getPrefix());
        assertEquals("c/", CliSyntax.PREFIX_CLASS.getPrefix());
        assertEquals("t/", CliSyntax.PREFIX_TAG.getPrefix());
        assertEquals("desc/", CliSyntax.PREFIX_NOTE.getPrefix());
    }

    @Test
    public void prefixConstants_areNotNull() {
        // Verify all prefix constants are properly initialized
        assertNotNull(CliSyntax.PREFIX_NAME);
        assertNotNull(CliSyntax.PREFIX_PHONE);
        assertNotNull(CliSyntax.PREFIX_EMAIL);
        assertNotNull(CliSyntax.PREFIX_ADDRESS);
        assertNotNull(CliSyntax.PREFIX_CLASS);
        assertNotNull(CliSyntax.PREFIX_TAG);
        assertNotNull(CliSyntax.PREFIX_NOTE);
    }

    @Test
    public void prefixConstants_toStringReturnsCorrectValues() {
        // Test that toString() method of prefixes returns expected values
        assertEquals("n/", CliSyntax.PREFIX_NAME.toString());
        assertEquals("p/", CliSyntax.PREFIX_PHONE.toString());
        assertEquals("e/", CliSyntax.PREFIX_EMAIL.toString());
        assertEquals("a/", CliSyntax.PREFIX_ADDRESS.toString());
        assertEquals("c/", CliSyntax.PREFIX_CLASS.toString());
        assertEquals("t/", CliSyntax.PREFIX_TAG.toString());
        assertEquals("desc/", CliSyntax.PREFIX_NOTE.toString());
    }

    @Test
    public void prefixConstants_areUniqueValues() {
        // Verify that all prefixes have unique string values
        String[] prefixValues = {
            CliSyntax.PREFIX_NAME.toString(),
            CliSyntax.PREFIX_PHONE.toString(),
            CliSyntax.PREFIX_EMAIL.toString(),
            CliSyntax.PREFIX_ADDRESS.toString(),
            CliSyntax.PREFIX_CLASS.toString(),
            CliSyntax.PREFIX_TAG.toString(),
            CliSyntax.PREFIX_NOTE.toString()
        };

        // Check that all values are different from each other
        for (int i = 0; i < prefixValues.length; i++) {
            for (int j = i + 1; j < prefixValues.length; j++) {
                assertNotNull(prefixValues[i]);
                assertNotNull(prefixValues[j]);
                // Each prefix should be unique (not equal to any other)
                if (prefixValues[i].equals(prefixValues[j])) {
                    throw new AssertionError("Duplicate prefix values found: " + prefixValues[i]);
                }
            }
        }
    }
}
