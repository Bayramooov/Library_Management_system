package main.common;
import database.Book;
import database.Funcs;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.DataCollection;

import java.util.ArrayList;
import java.util.Hashtable;

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
    public static Hashtable<String, Integer> stats;
    public static ArrayList<Book> top3Books;
    // Initializing from database
    public void initialize() throws Exception {

        totalBooks.setText(stats.get("allBooks").toString());
        borrowedBooks.setText(stats.get("borrowedBooks").toString());
        subjects.setText(stats.get("subjects").toString());
        types.setText(stats.get("types").toString());
        readers.setText(stats.get("readers").toString());
        ArrayList<Book> top3Books = Funcs.GetTop3Books(Login.con);
        booksName_1.setText(top3Books.get(0).Title);
        authorName_1.setText(top3Books.get(0).Author);
        booksName_2.setText(top3Books.get(1).Title);
        authorName_2.setText(top3Books.get(1).Author);
        booksName_3.setText(top3Books.get(2).Title);
        authorName_3.setText(top3Books.get(2).Author);
    }
}