package main.common;

import database.Funcs;
import database.User;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DataCollection;

public class Readers_C {
    @FXML
    public TextField searchText;
    @FXML
    private TableView tableView;

    public static User chosenUserInTable = null;

    // initializing tableView
    public void initialize() {

        if(MainFrame.pressedPanel.equals("Readers")) {
            initUsers("readers");
        }
        else if(MainFrame.pressedPanel.equals("Blocked Readers")) {
            initUsers("blockedReaders");
        }
        else if(MainFrame.pressedPanel.equals("Librarians")) {
            initUsers("librarians");
        }
    }

    // Handles the mouseClick of TableView
    public void handleMouseClick(MouseEvent mouseEvent) {

    }

    private void initUsers(String string) {
        TableColumn<User, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(200.0); IDColumn.setMaxWidth(200.0);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));


        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(280.0); IDColumn.setMaxWidth(280.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(280.0); IDColumn.setMaxWidth(280.0);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        if(string.equals("readers")) {
            tableView.setItems(DataCollection.observableReadersList);
        }
        else if(string.equals("blockedReaders")) {
            tableView.setItems(DataCollection.observableBlockedReadersList);
        }
        else {

            tableView.setItems(DataCollection.observableLibrarianList);
        }
        tableView.getColumns().addAll(IDColumn, nameColumn, passwordColumn);
        tableView.setEditable(true);
    }
    private void flushTableView()
    {
        tableView.getItems().removeAll();
        tableView.setItems(DataCollection.observableSearchingUsersList);
    }
    public void onSearchButtonPressed(ActionEvent actionEvent) throws Exception
    {
        if(MainFrame.pressedPanel.equals("Readers"))
           Funcs.SearchUsers(Login.con, UserType.Reader,false,searchText.getText());
        else if(MainFrame.pressedPanel.equals("Blocked Readers"))
            Funcs.SearchUsers(Login.con, UserType.Reader,true,searchText.getText());
        else if(MainFrame.pressedPanel.equals("Librarians"))
            Funcs.SearchUsers(Login.con, UserType.Librarian,false,searchText.getText());
        else
            throw new Exception("Pash Nax");
        flushTableView();
    }
}
