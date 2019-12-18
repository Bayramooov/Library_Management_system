package main.common;
import database.Book;
import database.BorrowedBook;
import database.Funcs;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import main.DataCollection;
import main.Main;

import java.io.IOException;

import static main.DataCollection.*;

public class Login {
    @FXML
    private Label welcomeMessage;
    @FXML
    private TextField login_userIDField;
    @FXML
    private PasswordField login_passwordField;
    @FXML
    private Button login_loginButton;

    public static String username;

    // Authenticating User
    @FXML
    public void login(ActionEvent event) throws Exception {

        currentUser = database.Funcs.LoginUser(Main.con,login_userIDField.getText(),login_passwordField.getText());
        //  TEMPORARY !!!
        if(currentUser != null) {
            username = currentUser.getName();
            importBooks();
            if(currentUser.getUType() == UserType.Admin) {
                Main.setScene("/main/common/AdminFrame.fxml");
            } else if(currentUser.getUType() == UserType.Librarian) {
                Main.setScene("/main/common/LibrarianFrame.fxml");
            } else if(currentUser.getUType() == UserType.Reader)
                if(!currentUser.Blocked) {
                    Main.setScene("/main/common/ReaderFrame.fxml");
                } else {
                    welcomeMessage.setText("Blocked User!");
                    welcomeMessage.setTextFill(Paint.valueOf("#FF8080"));
                    login_passwordField.clear();
                }
            }
         else {
            welcomeMessage.setText("Invalid Input!");
            welcomeMessage.setTextFill(Paint.valueOf("#FF8080"));
            login_passwordField.clear();
        }
    }

    private void importBooks() throws Exception {
        observableBookList.addAll(Funcs.GetAllBooks(Main.con));
        observableBorrowedBookList.addAll(Funcs.GetAllBorrowedBooks(Main.con));
        if(currentUser.getUType() == UserType.Reader) {
            for(BorrowedBook b: observableBorrowedBookList) {
                if(b.UserId.equals(currentUser.ID))
                observableMyBookList.add(b);
            }
        }
    }
}