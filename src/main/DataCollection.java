package main;

import database.Book;
import database.BorrowedBook;
import database.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataCollection {
    public static User currentUser;
    public static ObservableList<Book> observableBookList = FXCollections.observableArrayList();
    public static ObservableList<BorrowedBook> observableBorrowedBookList = FXCollections.observableArrayList();
    public static ObservableList<User> observableReadersList = FXCollections.observableArrayList();
    public static ObservableList<User> observableLibrarianList = FXCollections.observableArrayList();
    public static ObservableList<User> observableBlockedReadersList = FXCollections.observableArrayList();
    public static ObservableList<BorrowedBook> observableMyBookList = FXCollections.observableArrayList();
    public static ObservableList<Book> observableSearchingBooksList = FXCollections.observableArrayList();
    public static ObservableList<BorrowedBook> observableSearchingBorrowedBooksList = FXCollections.observableArrayList();
    public static ObservableList<User> observableSearchingUsersList=FXCollections.observableArrayList();
}
