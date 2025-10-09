package seedu.address.model.person;

public class Note {
    public final String value;

    public Note(String value) {
        this.value =  value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note)) {
            return false;
        }

        Note e = (Note) other;
        return value.equals(e.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
