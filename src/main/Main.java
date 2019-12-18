package main;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.common.Login;

import java.io.IOException;

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

    public static void main(String[] args) throws Exception {


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