package main.common;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.DataCollection;
import main.Main;

import java.io.IOException;

public class MainFrame {
    public static String pressedPanel;
    @FXML
    public BorderPane mainFrame;
    @FXML
    private Label username;
    @FXML
    private Label panelName;
    @FXML
    private Button headerBorder_addButton;
    @FXML
    private Button headerBorder_logoutButton;
    @FXML
    private Button leftBorder_dashboardButton;
    @FXML
    private Button leftBorder_booksButton;
    @FXML
    private Button leftBorder_myBooksButton;
    @FXML
    private Button leftBorder_borrowedBooksButton;
    @FXML
    private Button leftBorder_readersButton;
    @FXML
    private Button leftBorder_blockedReadersButton;



    // NEW EDIT BUTTON
    @FXML
    private Button headerBorder_editButton;
    @FXML
    public void handleEditButton() throws IOException {
        if(pressedPanel.equals("Readers")) {
            if(DataCollection.currentUser.getUType() == database.UserType.Admin) {
                panelName.setText("Edit User");
                pressedPanel = "Edit User";
                GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/UserViewFrame.fxml"));
                mainFrame.setCenter(dashboardPanel);
            }
        }
        else if(pressedPanel.equals("Librarians")) {
            panelName.setText("Edit User");
            pressedPanel = "Edit User";
            GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/UserViewFrame.fxml"));
            mainFrame.setCenter(dashboardPanel);
        }
        else if(pressedPanel.equals("Books")) {
            panelName.setText("Edit Book");
            pressedPanel = "Edit Book";
            GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/BookViewFrame.fxml"));
            mainFrame.setCenter(dashboardPanel);
        }
        else{
            System.out.println("Error");
        }
    }
    ///////////////////////////////////////////


    //  INITIALIZING THE USERNAME & PANEL NAME. TO BE CONTINUED...
    public void initialize() throws IOException {
        username.setText(Login.username);
        handleDashboardButton();

    }

    //  Returning to LogIn Page
    @FXML
    public void handleLogOutButton(ActionEvent event) throws IOException {
        //  Saving all the changes to the database...
        //  ...
        //  Loading the Login page.
        Main.setScene("/main/common/Login.fxml");
    }

    // Opening Dashboard
    @FXML
    public void handleDashboardButton() throws IOException {
        panelName.setText("Dashboard");
        pressedPanel = "Dashboard";
        GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/DashboardPane.fxml"));
        mainFrame.setCenter(dashboardPanel);
    }

    // Opening List of Books
    @FXML
    public void handleBooksButton(ActionEvent event) throws IOException {
        panelName.setText("Books");
        pressedPanel = "Books";
        GridPane booksPanel = FXMLLoader.load(getClass().getResource("/main/common/BookList.fxml"));
        mainFrame.setCenter(booksPanel);
    }

    // shows List Of Books Borrowed By One Reader
    @FXML
    public void handleMyBooksButton(ActionEvent event) throws IOException {
        panelName.setText("My Books");
        pressedPanel = "My Books";
        GridPane panel = FXMLLoader.load(getClass().getResource("/main/common/BookList.fxml"));
        mainFrame.setCenter(panel);
    }

    // shows List Of All Books Borrowed By All Readers
    @FXML
    public void handleBorrowedBooksButton(ActionEvent event) throws IOException {
        panelName.setText("Borrowed Books");
        pressedPanel = "Borrowed Books";
        GridPane panel = FXMLLoader.load(getClass().getResource("/main/common/BookList.fxml"));
        mainFrame.setCenter(panel);
    }

    // shows List of Readers
    public void handleReadersButton(ActionEvent actionEvent) throws IOException {
        panelName.setText("Readers");
        pressedPanel = "Readers";
        GridPane readersPanel = FXMLLoader.load(getClass().getResource("/main/common/Readers.fxml"));
        mainFrame.setCenter(readersPanel);
    }

    // shows List of Librarians
    public void handleLibrariansButton(ActionEvent actionEvent) throws IOException {
        panelName.setText("Librarians");
        pressedPanel = "Librarians";
        GridPane panel = FXMLLoader.load(getClass().getResource("/main/common/Readers.fxml"));
        mainFrame.setCenter(panel);
    }

    public void handleBlockedReadersButton(ActionEvent actionEvent) throws IOException {
        panelName.setText("Blocked Readers");
        pressedPanel = "Blocked Readers";
        GridPane blockedReaders = FXMLLoader.load(getClass().getResource("/main/common/Readers.fxml"));
        mainFrame.setCenter(blockedReaders);
    }


    @FXML
    public void handleAddButton() throws IOException {

        if(pressedPanel.equals("Readers")) {
            if(DataCollection.currentUser.getUType() == database.UserType.Admin) {
                panelName.setText("New User");
                pressedPanel = "New User";
                GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/UserViewFrame.fxml"));
                mainFrame.setCenter(dashboardPanel);
            }
        }
        else if(pressedPanel.equals("Librarians")) {
            panelName.setText("New User");
            pressedPanel = "New User";
            GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/UserViewFrame.fxml"));
            mainFrame.setCenter(dashboardPanel);
        }
        else if(pressedPanel.equals("Books")) {
            panelName.setText("New Book");
            pressedPanel = "New Book";
            GridPane dashboardPanel = FXMLLoader.load(getClass().getResource("/main/common/BookViewFrame.fxml"));
            mainFrame.setCenter(dashboardPanel);
        }
        else{
            System.out.println("Error");
        }

    }

}