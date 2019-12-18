package main;

import java.sql.*;
import database.Funcs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.common.DashboardPane_C;
import main.common.Login;
import java.io.IOException;
import static main.common.Login.*;


public class Main extends Application {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("/main/common/Login.fxml"));
        stage = primaryStage;
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/main/img/icon.png")));
        stage.setTitle("Library Management System");
        scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("/main/common/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static class Database extends Thread
    {
        public void run()
        {
            try
            {
                Funcs.GetAllBooks(con);
                Funcs.GetAllBorrowedBooks(con);
                Funcs.GetLibrarians(con);
                Funcs.GetBlockedUsers(con);
                Funcs.GetReaders(con);
            }
            catch (Exception e){}
        }
    }

    static class UpdateConnection extends Thread
    {
        public void run()
        {
            try
            {
                while(true) {
                    DashboardPane_C.stats = Funcs.GetCountStatistics(Login.con);
                    DashboardPane_C.top3Books = Funcs.GetTop3Books(con);
                    Thread.sleep(180000);
                    String url = "jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH"; // database url
                    String userName = "RWX3BuLgCH";
                    String password = "2WMYapbyjH";
                    Class.forName("com.mysql.jdbc.Driver");
                    Login.con = DriverManager.getConnection(url, userName, password); // connecting to database
                }
            }
            catch (Exception e){}
        }
    }

    public static void main(String[] args) throws Exception {

        String url="jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH"; // database url
        String userName="RWX3BuLgCH";
        String password="2WMYapbyjH";
        Class.forName("com.mysql.jdbc.Driver");
        Login.con= DriverManager.getConnection(url,userName,password); // connecting to database
        new UpdateConnection().start();
        new Database().start();
        launch(args);
        System.exit(1);
    }

    //  SWITCHES THE SCENES OF THE STAGE (WINDOW)
    public static void setScene(String fileName) throws IOException {
        root = FXMLLoader.load(Main.class.getResource(fileName));
        scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(Main.class.getResource("/main/common/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}