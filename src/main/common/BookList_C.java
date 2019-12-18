package main.common;

import database.Book;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import main.DataCollection;


public class BookList_C {
    @FXML
    private TableView tableView;

    // initializing tableView
    public void initialize() {
        if(MainFrame.pressedPanel.equals("Books")) {
            TableColumn<database.Book, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setMinWidth(250.0);
            titleColumn.setMaxWidth(250.0);
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));


            TableColumn<database.Book, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setMinWidth(250.0);
            authorColumn.setMaxWidth(250.0);
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));

            TableColumn<database.Book, String> subjectColumn = new TableColumn<>("Subject");
            subjectColumn.setMinWidth(250.0);
            subjectColumn.setMaxWidth(250.0);
            subjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
            tableView.setItems(DataCollection.observableBookList);
            tableView.getColumns().addAll(titleColumn, authorColumn, subjectColumn);
        }
    }

    // Handles the mouseClick of TableView
    public void handleMouseClick(MouseEvent mouseEvent) {
       // database.Book temp = (database.Book) tableView.getSelectionModel().getSelectedItem();
        //DataCollection.observableBookList.add(temp);
/*        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuItem1 = new MenuItem("Issue");
        MenuItem menuItem2 = new MenuItem("Delete");
        MenuItem menuItem3 = new MenuItem("Edit");
        MenuItem menuItem4 = new MenuItem("Return");
        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
*/

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    if (mouseEvent.getClickCount() == 2) {

                    }
                }
            }
        });
/*
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableView, t.getScreenX(), t.getScreenY());
                }
            }
        });*/

    }

  /*  public void showInfo(Book temp) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("" + temp.getID());
        alert.setResizable(false);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setContentText("TITLE: " + temp.getTitle());
        alert.setContentText("Author: " + temp.getAuthor());

        alert.showAndWait();
    }
*/

  /*  public String tex() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Will you return a book or borrow?");
        dialog.setHeaderText("Enter Reader ID");
        dialog.getDialogPane();

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }*/
}
