package main.common;
import database.Funcs;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static main.DataCollection.*;

public class Login {
    public static Connection con=null;
    @FXML
    private Label welcomeMessage;
    @FXML
    private TextField login_userIDField;
    @FXML
    private PasswordField login_passwordField;
    @FXML
    private Button login_loginButton;

    public static String username;

    public void initialize() throws Exception {
        String url="jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH"; // database url
        String userName="RWX3BuLgCH";
        String password="2WMYapbyjH";
        Class.forName("com.mysql.jdbc.Driver");
        Login.con= DriverManager.getConnection(url,userName,password); // connecting to database
    }


    // Authenticating User
    @FXML
    public void login(ActionEvent event) throws Exception {

        currentUser = database.Funcs.LoginUser(con,login_userIDField.getText(),login_passwordField.getText());
        //  TEMPORARY !!!
        if(currentUser != null) {
            username = currentUser.getName();
            if(currentUser.getUType() == UserType.Admin) {
                Main.setScene("/main/common/AdminFrame.fxml");
            } else if(currentUser.getUType() == UserType.Librarian) {
                Main.setScene("/main/common/LibrarianFrame.fxml");
            } else if(currentUser.getUType() == UserType.Reader)
                if(!currentUser.Blocked) {
                    importMyBooksList();
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

    public static void importBooks() throws Exception {
        observableBookList.addAll(Funcs.GetAllBooks(con));
        observableBorrowedBookList.addAll(Funcs.GetAllBorrowedBooks(con));
    }

    public static void importReaders() throws Exception {
        observableReadersList.addAll(Funcs.GetReaders(con));
        observableBlockedReadersList.addAll(Funcs.GetBlockedUsers(con));
    }

    public static void importLibrarians() throws Exception {
        observableLibrarianList.addAll(Funcs.GetLibrarians(con));
    }

    private void importMyBooksList() throws Exception {
        observableMyBookList.clear();
        observableMyBookList.addAll(Funcs.GetBorrowedBooksOfReader(con,currentUser.TableId));
    }

}