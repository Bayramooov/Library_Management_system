package main.common;

import database.BorrowedBook;
import database.Funcs;
import database.User;
import database.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.DataCollection;

import java.util.Optional;

import static main.DataCollection.currentUser;

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
        chosenUserInTable = (User)tableView.getSelectionModel().getSelectedItem();
        tableView.setOnMouseClicked(mouseEvent1 -> {
            if (mouseEvent1.getButton().equals(MouseButton.SECONDARY)) {
                if(MainFrame.pressedPanel.equals("Blocked Readers")) {
                    unblockReaders(mouseEvent1);
                }
                else {
                    showContextMenuForReaders(mouseEvent1);
                }
            }
        });
    }

    private void showContextMenuForReaders(MouseEvent mouseEvent1) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Block");
        MenuItem item3 = new MenuItem("Delete");
        contextMenu.getItems().add(item1);
        if(currentUser.getUType() == UserType.Admin) {
            contextMenu.getItems().add(item3);
        }
        contextMenu.show(tableView,mouseEvent1.getScreenX(),mouseEvent1.getScreenY());
        item1.setOnAction(event -> {
            try {
                chosenUserInTable = (User) tableView.getSelectionModel().getSelectedItem();
                Funcs.BlockOrUnblockUser(Login.con,chosenUserInTable.TableId,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataCollection.observableReadersList.remove(chosenUserInTable);
            chosenUserInTable.setBlocked(true);
            DataCollection.observableBlockedReadersList.add(chosenUserInTable);
            DataCollection.observableReadersList.add(chosenUserInTable);
            chosenUserInTable = null;
            }
        );


        item3.setOnAction(event-> {
            try
            {chosenUserInTable = (User) tableView.getSelectionModel().getSelectedItem();
                Funcs.DeleteUser(Login.con,chosenUserInTable.TableId,chosenUserInTable.getUType());
                if(chosenUserInTable.getUType() == UserType.Reader) {
                    DataCollection.observableReadersList.add(chosenUserInTable);
                } else {
                    DataCollection.observableLibrarianList.remove(chosenUserInTable);
                }
                chosenUserInTable = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void unblockReaders(MouseEvent mouseEvent1) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Unblock");
        contextMenu.getItems().add(item1);
        contextMenu.show(tableView,mouseEvent1.getScreenX(),mouseEvent1.getScreenY());
        item1.setOnAction(event -> {
            contextMenu.setAutoHide(true);
            try {
                Funcs.BlockOrUnblockUser(Login.con,chosenUserInTable.TableId,false);
                DataCollection.observableBlockedReadersList.remove(chosenUserInTable);
                DataCollection.observableReadersList.remove(chosenUserInTable);
                chosenUserInTable.setBlocked(false);
                DataCollection.observableReadersList.add(chosenUserInTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            chosenUserInTable = null;
        });
    }

    private void initUsers(String string) {

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(436.0); nameColumn.setMaxWidth(436.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<User, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(120.0); IDColumn.setMaxWidth(120.0);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(207.0); passwordColumn.setMaxWidth(207.0);
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
        tableView.getColumns().addAll(nameColumn, IDColumn, passwordColumn);
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
