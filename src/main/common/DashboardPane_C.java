package main.common;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardPane_C {
    //  INITIALIZATION OF ALL THE LABELS...
    @FXML
    private Label totalBooks;
    @FXML
    private Label borrowedBooks;
    @FXML
    private Label subjects;
    @FXML
    private Label types;
    @FXML
    private Label readers;
    @FXML
    private Label booksName_1;
    @FXML
    private Label authorName_1;
    @FXML
    private Label booksName_2;
    @FXML
    private Label authorName_2;
    @FXML
    private Label booksName_3;
    @FXML
    private Label authorName_3;

    //  INITIALIZATION OF ALL THE LABELS...
    //  WILL BE LOADED FROM THE DATABASE LATER...
    public void initialize() {
        totalBooks.setText("21.2K");
        borrowedBooks.setText("7.3K");
        subjects.setText("372");
        types.setText("1.72K");
        readers.setText("2.8K");
        booksName_1.setText("HTML5 - Up and Running");
        authorName_1.setText("Mark Pilgrim");
        booksName_2.setText("CSS - in Depth");
        authorName_2.setText("Keith J. Grant");
        booksName_3.setText("Java - How to program");
        authorName_3.setText("Paul Deitel & Harvey Deitel");
    }
}