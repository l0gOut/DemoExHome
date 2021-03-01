package ru.sapteh;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ru/sapteh/model/entrance.fxml")));
        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Вход");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
