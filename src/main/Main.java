package main;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    public static Connection con=null;

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

    public static void main(String[] args) throws Exception {
        String url="jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH"; // database url
        String userName="RWX3BuLgCH";
        String password="2WMYapbyjH";
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection(url,userName,password); // connecting to database

        launch(args);
    }

    //  SWITCHES THE SCENES OF THE STAGE (WINDOW)
    public static void setScene(String fileName) throws IOException {
        // stage.close();
        root = FXMLLoader.load(Main.class.getResource(fileName));
        scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(Main.class.getResource("/main/common/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}