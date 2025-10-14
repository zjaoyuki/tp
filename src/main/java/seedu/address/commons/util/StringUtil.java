package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and can match substrings (partial names).
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true //even though not a full word match
     *       </pre>
     * @param sentence cannot be null.
     * @param word cannot be null, cannot be empty, must be a single word.
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase().trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(wordInSentence -> wordInSentence.toLowerCase().contains(preppedWord));
    }

    /**
     * Returns true if {@code tagList} contains the {@code wordToFind}.
     *   Ignores case and can match partial tag substrings.
     *   <br>examples:<pre>
     *       containsTagIgnoreCase( [friend, colleague, coursemate], friend) == true
     *       containsTagIgnoreCase( [friend, colleague, coursemate], FRIEND coll) == true
     *       </pre>
     * @param tagList The list of tags associated with a person.
     * @param wordToFind cannot be null, cannot be empty, must be a single word.
     * @return Whether the tagList contains the tag of wordToFind.
     */
    public static boolean containsTagIgnoreCase(Set<Tag> tagList, String wordToFind) {
        requireNonNull(wordToFind);
        requireNonNull(tagList);

        String preppedWord = wordToFind.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String preppedSentence = tagList.toString().toLowerCase().trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(wordInSentence -> wordInSentence.toLowerCase().contains(preppedWord));
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw;
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
