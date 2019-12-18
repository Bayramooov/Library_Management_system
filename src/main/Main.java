package main;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import java.util.ArrayList;

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
        String url="jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH";
        String userName="RWX3BuLgCH";
        String password="2WMYapbyjH";
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection(url,userName,password);

        initBooks();
    //  initUsers();
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

    public static void initBooks() throws Exception {

        ArrayList<database.Book> x = database.Funcs.GetAllBooks(con);
        for(database.Book b: x) {
            DataCollection.observableBookList.add(b);
        }
    }

    /*public static void initUsers() {
        User user1 = new User("U1810036","Kamronbek Rustamov", "1111",10, 0, UserType.Reader);
        User user2 = new User("U1810038","Abdullaev Jaloliddin", "1111",3,1, UserType.Reader);
        User user3 = new User("U1810054","Sadullaev Sobirjon", "1111",5,0, UserType.Reader);
        User user4 = new User("U1810087","Akhmedov Fazliddin", "1111",11,1, UserType.Reader);
        User user5 = new User("U1810075","Rajabov Javokhir", "1111",12,0, UserType.Reader);
        User.observableUserList.add(user1);
        User.observableUserList.add(user2);
        User.observableUserList.add(user3);
        User.observableUserList.add(user4);
        User.observableUserList.add(user5);
    }*/
}