package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import seedu.address.model.person.Person;

/**
 * A popup window for confirmation after a person executes delete contact command.
 */
public class DeletePopup extends UiPart<Stage> {

    private static final String FXML = "DeletePopup.fxml";

    @FXML
    private Label headerLabel;
    @FXML
    private ListView<Person> personListView;
    @FXML
    private TextField inputField;

    private List<Person>  matchingResults;
    private boolean confirmed = false;
    private Person selectedPerson = null;

    /**
     * Creates a new Delete Pop up.
     *
     * @param root Stage to use as the root of the Delete Pop up.
     */
    public DeletePopup(Stage root) {
        super(FXML, root);
        setUpKeyboardHandlers();
    }

    /**
     * Creates a new Delete Pop up.
     */
    public DeletePopup() {
        this(new Stage());
    }

    public void show(String headerMessage, List<Person> matchingResults) {
        this.matchingResults = matchingResults;
        this.confirmed = false;
        this.selectedPerson = null;
        headerLabel.setText(headerMessage);

        personListView.setItems(FXCollections.observableArrayList(
                matchingResults.stream()
                        .map(Person::toString)
                        .toList()
        ));

        inputField.clear();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    private void setUpKeyboardHandlers() {

    }

    private void handleEnter() {

    }

    private void handleEscape() {

    }

    public void close() {
        getRoot().hide();
    }
}
