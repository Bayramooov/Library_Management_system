package main.common;
import database.Funcs;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.DataCollection;

import java.time.LocalDate;

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
    private Button newUserCreateButton;
    @FXML
    private Button newUserCancelButton;

    //  Book
    @FXML
    private GridPane BookViewPanel;
    @FXML
    private DatePicker publishedDate;
    @FXML
    private TextField newBookTitle;
    @FXML
    private TextField newBookAuthor;
    @FXML
    private TextField newBookSubject;
    @FXML
    private TextField newBookNumbers;
    @FXML
    private Button newBooksCreateButton;
    @FXML
    private Button newBookCancelButton;


    public void handleCreateNewUserButton(ActionEvent actionEvent) throws Exception {
        if(newUserType.getSelectionModel().getSelectedItem().equals("Reader")) {
            Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Reader);
        } else {
            Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Librarian);
        }
        DataCollection.observableReadersList.add(Funcs.LoginUser(Login.con,newUserID.getText(),newUserPass.getText()));
    }

    public void handleCreateBookButton(ActionEvent actionEvent) throws Exception {
        Funcs.AddNewBook(Login.con,newBookTitle.getText(),newBookAuthor.getText(),publishedDate.getValue(),newBookSubject.getText(),Integer.parseInt(newBookNumbers.getText()));
    }
}
