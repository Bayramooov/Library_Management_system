package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private String ID;
    private String name;
    private String password;
    private int numberOfBorrowedBooks;
    private int blocked;
    private UserType userType;

    public static ObservableList<User> observableUserList = FXCollections.observableArrayList();

    public User() {
    }

    public User(String ID, String name, String password,int numberOfBorrowedBooks, int blocked, UserType userType) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.numberOfBorrowedBooks = numberOfBorrowedBooks;
        this.blocked = blocked;
        this.userType = userType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfBorrowedBooks() {
        return numberOfBorrowedBooks;
    }

    public void setNumberOfBorrowedBooks(int numberOfBorrowedBooks) {
        this.numberOfBorrowedBooks = numberOfBorrowedBooks;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
