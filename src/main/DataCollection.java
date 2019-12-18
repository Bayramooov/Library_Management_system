package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataCollection {
    public static database.User currentUser;
    public static ObservableList<database.Book> observableBookList = FXCollections.observableArrayList();
    public static ObservableList<database.BorrowedBook> observableBorrowedBookList = FXCollections.observableArrayList();
    public static ObservableList<database.User> observableReadersList = FXCollections.observableArrayList();
    public static ObservableList<database.User> observableLibrarianList = FXCollections.observableArrayList();
    public static ObservableList<database.User> observableBlockedReadersList = FXCollections.observableArrayList();
    public static ObservableList<database.BorrowedBook> observableMyBookList = FXCollections.observableArrayList();
}
