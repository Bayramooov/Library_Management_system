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

    //  INITIALIZATION OF ALL THE LABELS...
    //  WILL BE LOADED FROM THE DATABASE LATER...
    public void initialize() throws Exception {
        Hashtable<String, Integer> stats = Funcs.GetCountStatistics(Login.con);

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