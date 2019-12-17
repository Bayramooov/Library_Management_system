package main.common;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import main.Main;

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


    @FXML
    public void login(ActionEvent event) throws IOException {
        //  Checking user inputs process...
        //  ...
        //  by the time authentication finishes & Loads the MainFrame page.

        //  TEMPORARY !!!
        if(login_passwordField.getText().equals("1")) {
            if(login_userIDField.getText().equals("A")) {
                username = "Kamronbek Rustamov";
                Main.setScene("/main/common/AdminFrame.fxml");
            } else if(login_userIDField.getText().equals("L")) {
                username = "Kamronbek Rustamov";
                Main.setScene("/main/common/LibrarianFrame.fxml");
            } else {
                username = login_userIDField.getText();
                Main.setScene("/main/common/ReaderFrame.fxml");
            }
        } else {
            welcomeMessage.setText("User Not Found!");
            welcomeMessage.setTextFill(Paint.valueOf("#FF8080"));
            login_passwordField.clear();
        }
    }
}