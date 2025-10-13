package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("")
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + CATEGORY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB)
                .withNote("").withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_nameNormalization_success() {
        // Multiple spaces in name should be normalized to single space
        Person expectedPerson = new PersonBuilder().withName("John Doe").withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();

        assertParseSuccess(parser, " n/John    Doe " + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Leading and trailing spaces should be trimmed
        assertParseSuccess(parser, " n/  John Doe  " + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_phoneNormalization_success() {
        // Phone with spaces should be normalized
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("98765432")
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();

        assertParseSuccess(parser, NAME_DESC_BOB + " p/9876 5432" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Phone with dashes should be normalized
        assertParseSuccess(parser, NAME_DESC_BOB + " p/9876-5432" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Phone with mixed spaces and dashes should be normalized
        assertParseSuccess(parser, NAME_DESC_BOB + " p/98 76-54 32" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_categoryNormalization_success() {
        // Mixed case category should be normalized to lowercase
        Person expectedStudentPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory("student").withNote("").build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + " c/Student",
                new AddCommand(expectedStudentPerson));

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + " c/STUDENT",
                new AddCommand(expectedStudentPerson));

        Person expectedColleaguePerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory("colleague").withNote("").build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + " c/Colleague",
                new AddCommand(expectedColleaguePerson));

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + " c/COLLEAGUE",
                new AddCommand(expectedColleaguePerson));
    }

    @Test
    public void parse_emailNormalization_success() {
        // Mixed case email should be normalized to lowercase
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail("john.doe@gmail.com").withCategory(VALID_CATEGORY_BOB).withNote("").build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + " e/John.Doe@Gmail.Com" + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_validNameFormats_success() {
        // Names with hyphens
        Person expectedPerson = new PersonBuilder().withName("Mary-Jane").withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();
        assertParseSuccess(parser, " n/Mary-Jane" + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Names with apostrophes
        expectedPerson = new PersonBuilder().withName("O'Connor").withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();
        assertParseSuccess(parser, " n/O'Connor" + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Names with multiple words, hyphens, and apostrophes
        expectedPerson = new PersonBuilder().withName("Mary-Jane O'Connor Smith").withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();
        assertParseSuccess(parser, " n/Mary-Jane O'Connor Smith" + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_validPhoneNumbers_success() {
        // Phone starting with 8
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("81234567")
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();
        assertParseSuccess(parser, NAME_DESC_BOB + " p/81234567" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));

        // Phone starting with 9
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("91234567")
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").build();
        assertParseSuccess(parser, NAME_DESC_BOB + " p/91234567" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + CATEGORY_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple categories
        assertParseFailure(parser, CATEGORY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CATEGORY));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + CATEGORY_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_CATEGORY, PREFIX_EMAIL, PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withCategory(VALID_CATEGORY_BOB).withNote("").withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + CATEGORY_DESC_BOB,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_CATEGORY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_CATEGORY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name - contains numbers
        assertParseFailure(parser, " n/John123" + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid name - contains symbols
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid name - empty after trimming
        assertParseFailure(parser, " n/   " + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone - wrong length (too short)
        assertParseFailure(parser, NAME_DESC_BOB + " p/1234567" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid phone - wrong length (too long)
        assertParseFailure(parser, NAME_DESC_BOB + " p/123456789" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid phone - wrong starting digit
        assertParseFailure(parser, NAME_DESC_BOB + " p/71234567" + EMAIL_DESC_BOB + CATEGORY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid phone - contains letters
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid category - not student or colleague
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + " c/teacher",
                Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_CATEGORY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Category.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + CATEGORY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, seedu.address.model.tag.Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_CATEGORY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + CATEGORY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
