package main.common;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class creatingFrame {
    //  User
    @FXML
    private GridPane UserViewPanel;
    @FXML
    private TextField newUserName;
    @FXML
    private TextField newUserID;
    @FXML
    private ComboBox newUserType;
    @FXML
    private PasswordField newUserPass;
    @FXML
    private Label errorMessage;
    @FXML
    private Button createUserButton;
    @FXML
    private Button newUserCancelButton;

    //  Book
    @FXML
    private TextField newBookTitle;
    @FXML
    private TextField newBookAuthor;
    @FXML
    private TextField newBookSubject;
    @FXML
    private TextField newBookNumbers;
    @FXML
    private TextField newBookISBN;
    @FXML
    private Button newBooksCreateButton;
    @FXML
    private Button newBookCancelButton;
}
