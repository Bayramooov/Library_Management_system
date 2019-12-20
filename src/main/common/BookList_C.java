package main.common;

import database.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import main.DataCollection;

import java.io.IOException;
import java.util.Optional;

import static main.DataCollection.currentUser;
import static main.DataCollection.observableReadersList;


public class BookList_C {
    @FXML
    private TextField searchingText;
    @FXML
    private TableView tableView;

    private String username;
    public static Book book = null;

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
        if(MainFrame.pressedPanel.equals("Borrowed Books")) {}
        else {
            book = (Book) tableView.getSelectionModel().getSelectedItem();}
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
    }

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
            contextMenu.setAutoHide(true);
            try {
                Funcs.ReturnBook(Login.con,b.BookId,b.BorrowerId);
                DataCollection.observableBorrowedBookList.remove(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showContextMenuForBooks(MouseEvent mouseEvent) {
        if(currentUser.getUType() == UserType.Reader) {
        } else {
            book = (Book)tableView.getSelectionModel().getSelectedItem();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem item1 = new MenuItem("Borrow");
            MenuItem item3 = new MenuItem("Delete");
            contextMenu.getItems().add(item3);
            if(currentUser.getUType() == UserType.Librarian) {
                contextMenu.getItems().add(item1);
            }
            contextMenu.show(tableView,mouseEvent.getScreenX(),mouseEvent.getScreenY());
            item1.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Borrowing Book");
            dialog.setHeaderText("Enter Reader ID");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()) {
             String s = result.toString().substring(9,17);
             username = s;
                try {
                    int i = Funcs.GetUserTableId(Login.con,username);
                    Funcs.BorrowBook(Login.con,book.ID,i);
                    book = null;
                    Funcs.GetAllBorrowedBooks(Login.con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        item3.setOnAction(event-> {
            try {
                Funcs.DeleteBook(Login.con, book.ID);
                DataCollection.observableBookList.remove(book);
                book = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        }
    }

}
