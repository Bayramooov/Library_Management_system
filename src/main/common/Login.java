package main.common;
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
import static main.DataCollection.currentUser;

import java.io.IOException;

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
            if(currentUser.getUType() == UserType.Admin) {
                Main.setScene("/main/common/AdminFrame.fxml");
            } else if(currentUser.getUType() == UserType.Librarian) {
                Main.setScene("/main/common/LibrarianFrame.fxml");
            } else if(currentUser.getUType() == UserType.Reader)
                Main.setScene("/main/common/ReaderFrame.fxml");
            }
         else {
            welcomeMessage.setText("Invalid Input!");
            welcomeMessage.setTextFill(Paint.valueOf("#FF8080"));
            login_passwordField.clear();
        }
    }
}