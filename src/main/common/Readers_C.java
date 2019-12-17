package main.common;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.User;

public class Readers_C {
    @FXML
    private TableView tableView;

    // initializing tableView
    public void initialize() {
        TableColumn<User, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(200.0); IDColumn.setMaxWidth(200.0);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));


        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(280.0); IDColumn.setMaxWidth(280.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, Integer> numberOfBorrowedBooksColumn = new TableColumn<>("Number Of Borrowed Books");
        numberOfBorrowedBooksColumn.setMinWidth(280.0); IDColumn.setMaxWidth(280.0);
        numberOfBorrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfBorrowedBooks"));
        tableView.setItems(User.observableUserList);
        tableView.getColumns().addAll(IDColumn, nameColumn, numberOfBorrowedBooksColumn);
        tableView.setEditable(true);
    }

    // Handles the mouseClick of TableView
    public void handleMouseClick(MouseEvent mouseEvent) {

    }
}
