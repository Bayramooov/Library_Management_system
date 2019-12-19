package main.common;
import database.Book;
import database.Funcs;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import main.DataCollection;

import java.nio.file.LinkOption;
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

    public void initialize() {
        if(MainFrame.pressedPanel.equals("Edit User")) {
            newUserName.setText(Readers_C.chosenUserInTable.Name);
            newUserID.setText(Readers_C.chosenUserInTable.ID);
            newUserPass.setText(Readers_C.chosenUserInTable.Password);
            newUserType.getSelectionModel().clearAndSelect(Readers_C.chosenUserInTable.getUType() == UserType.Reader?1:0);
            newUserCreateButton.setText("Change");
        }
        else if(MainFrame.pressedPanel.equals("Edit Book")) {
            newBookAuthor.setText(BookList_C.book.Author);
            newBookTitle.setText(BookList_C.book.Title);
            newBookSubject.setText(BookList_C.book.Subject);
            newBookNumbers.setText("" + BookList_C.book.AvailableBooks);
            newBooksCreateButton.setText("Change");
        }
    }


    public void handleCreateNewUserButton(ActionEvent actionEvent) throws Exception {

        if(MainFrame.pressedPanel.equals("New User")) {
            if(newUserType.getSelectionModel().getSelectedItem().equals("Reader")) {
                Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Reader);
                DataCollection.observableReadersList.add(Funcs.LoginUser(Login.con,newUserID.getText(),newUserPass.getText()));
            } else {
                Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Librarian);
                DataCollection.observableLibrarianList.add(Funcs.LoginUser(Login.con,newUserID.getText(),newUserPass.getText()));
            }
        } else {

            if(newUserType.getSelectionModel().getSelectedItem().equals("Reader")) {
            DataCollection.observableReadersList.remove(Readers_C.chosenUserInTable);
            Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Reader);
            DataCollection.observableReadersList.add(Funcs.LoginUser(Login.con,newUserID.getText(),newUserPass.getText()));
            } else {
                DataCollection.observableLibrarianList.remove(Readers_C.chosenUserInTable);
                Funcs.AddNewUser(Login.con,newUserID.getText(),newUserPass.getText(),newUserName.getText(), UserType.Librarian);
                DataCollection.observableLibrarianList.add(Funcs.LoginUser(Login.con,newUserID.getText(),newUserPass.getText()));
            }
        }
        Readers_C.chosenUserInTable = null;

    }

    public void handleCreateBookButton(ActionEvent actionEvent) throws Exception {
        if(MainFrame.pressedPanel.equals("Edit Book")) {
            DataCollection.observableBookList.remove(BookList_C.book);
        }

        Funcs.AddNewBook(Login.con,newBookTitle.getText(),newBookAuthor.getText(),publishedDate.getValue(),newBookSubject.getText(),Integer.parseInt(newBookNumbers.getText()));
        DataCollection.observableBookList.add(new Book(Funcs.GetBookTableId(Login.con,newBookTitle.getText()),newBookTitle.getText(),newBookAuthor.getText(),publishedDate.getValue().toString(),newBookSubject.getText(),Integer.parseInt(newBookNumbers.getText())));
        BookList_C.book = null;
    }
}
