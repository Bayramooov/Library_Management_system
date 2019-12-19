package main.common;

import database.Book;
import database.BorrowedBook;
import database.Funcs;
import database.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import main.DataCollection;

import java.util.Optional;

import static main.DataCollection.observableReadersList;


public class BookList_C {
    @FXML
    private TextField searchingText;
    @FXML
    private TableView tableView;

    // initializing tableView
    public void initialize() throws Exception {
        if(MainFrame.pressedPanel.equals("Books")) {
            initBooks();
        }
        else if(MainFrame.pressedPanel.equals("My Books")) {
            initMyBooks();
        } else if(MainFrame.pressedPanel.equals("Borrowed Books")) {
            initBorrowedBooks();
        }
    }

    // Handles the mouseClick of TableView
    public void handleMouseClick(MouseEvent mouseEvent) {
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

        tableView.setOnMouseClicked(mouseEvent1 -> {
            if (mouseEvent1.getButton().equals(MouseButton.SECONDARY)) {
                if(MainFrame.pressedPanel.equals("Borrowed Books")) {
                    returnBorrowedBook(mouseEvent1);
                }
                else {
                    showContextMenuForBooks(mouseEvent1);
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

    private void initBooks() throws Exception {
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

    private void searchingBooks() throws Exception {
      Funcs.SearchBooks(Login.con,this.searchingText.getText());
      tableView.getItems().removeAll();
      tableView.setItems(DataCollection.observableSearchingBooksList);

  }

    private void searchingBorrowedBooks(boolean isItReadersFrame) throws Exception
    {
      if(isItReadersFrame)
          Funcs.SearchBorrowedBooksOfReader(Login.con,DataCollection.currentUser.TableId,this.searchingText.getText());

      else
          Funcs.SearchBorrowedBooks(Login.con,this.searchingText.getText());

      tableView.getItems().clear();
      tableView.setItems(DataCollection.observableSearchingBorrowedBooksList);
  }

    private void initMyBooks() {

      TableColumn<database.BorrowedBook, String> titleColumn = new TableColumn<>("Title");
      titleColumn.setMinWidth(280.0);
      titleColumn.setMaxWidth(280.0);
      titleColumn.setCellValueFactory(new PropertyValueFactory<>("Book"));

      TableColumn<database.BorrowedBook, String> borrowedDateColumn = new TableColumn<>("Borrowed Date");
      borrowedDateColumn.setMinWidth(250.0);
      borrowedDateColumn.setMaxWidth(250.0);
      borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("BorrowedDate"));

      TableColumn<database.BorrowedBook, String> returnedDateColumn = new TableColumn<>("Return Date");
      returnedDateColumn.setMinWidth(250.0);
      returnedDateColumn.setMaxWidth(250.0);
      returnedDateColumn.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
      tableView.setItems(DataCollection.observableMyBookList);
      tableView.getColumns().addAll(titleColumn, borrowedDateColumn, returnedDateColumn);
  }

    private void initBorrowedBooks() {

      TableColumn<database.BorrowedBook, String> IDColumn = new TableColumn<>("Borrower ID");
      IDColumn.setMinWidth(280.0);
      IDColumn.setMaxWidth(280.0);
      IDColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));

      TableColumn<database.BorrowedBook, String> titleColumn = new TableColumn<>("Title");
      titleColumn.setMinWidth(280.0);
      titleColumn.setMaxWidth(280.0);
      titleColumn.setCellValueFactory(new PropertyValueFactory<>("Book"));

      TableColumn<database.BorrowedBook, String> borrowedDateColumn = new TableColumn<>("Borrowed Date");
      borrowedDateColumn.setMinWidth(250.0);
      borrowedDateColumn.setMaxWidth(250.0);
      borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("BorrowedDate"));

      TableColumn<database.BorrowedBook, String> returnedDateColumn = new TableColumn<>("Return Date");
      returnedDateColumn.setMinWidth(250.0);
      returnedDateColumn.setMaxWidth(250.0);
      returnedDateColumn.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
      tableView.setItems(DataCollection.observableBorrowedBookList);
      tableView.getColumns().addAll(titleColumn, borrowedDateColumn, returnedDateColumn);
  }

    public void handleSearchButton(ActionEvent actionEvent) throws Exception {
//
        if(MainFrame.pressedPanel.equals("Books"))
        {
            searchingBooks();
        }
        else if(MainFrame.pressedPanel.equals("Borrowed Books"))
        {
            searchingBorrowedBooks(false);
        }
        else if(MainFrame.pressedPanel.equals("My Books"))
        {
            searchingBorrowedBooks(true);
        }
    }
    public void returnBorrowedBook(MouseEvent mouseEvent) {
        BorrowedBook b = (BorrowedBook)tableView.getSelectionModel().getSelectedItem();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Return");
        contextMenu.getItems().add(item1);
        contextMenu.show(tableView,mouseEvent.getScreenX(),mouseEvent.getScreenY());
        item1.setOnAction(event -> {
            try {
                Funcs.ReturnBook(Login.con,b.BookId,b.BorrowerId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataCollection.observableBorrowedBookList.remove(b);
        });
    }

    public void showContextMenuForBooks(MouseEvent mouseEvent) {
        Book b = (Book)tableView.getSelectionModel().getSelectedItem();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Borrow");
        MenuItem item2 = new MenuItem("Edit");
        MenuItem item3 = new MenuItem("Delete");
        MenuItem item4 = new MenuItem("View");
        contextMenu.getItems().addAll(item1, item2, item3, item4);
        contextMenu.show(tableView,mouseEvent.getScreenX(),mouseEvent.getScreenY());
        item1.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Borrowing Book");
            dialog.setHeaderText("Enter Reader ID");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()) {

                try {
                    Funcs.BorrowBook(Login.con,b.ID,findBorrowerId(result.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int findBorrowerId(String s) {
        for(User u: DataCollection.observableReadersList) {
            if(u.ID.equals(s)) {
                return u.TableId;
            }
        }
        return 0;
    }
}
