package ru.sapteh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllerSelect {

    @FXML
    private Pane pane;
    @FXML
    private RadioButton ten;
    @FXML
    private RadioButton fifty;
    @FXML
    private RadioButton twoHundred;
    @FXML
    private RadioButton all;
    @FXML
    private Label label;

    @FXML
    void initialize(){
        ToggleGroup group = new ToggleGroup();
        ten.setToggleGroup(group);
        fifty.setToggleGroup(group);
        twoHundred.setToggleGroup(group);
        all.setToggleGroup(group);
    }

    @FXML
    public void button() throws Exception {
        if(ten.isSelected() || fifty.isSelected() || twoHundred.isSelected() || all.isSelected()) {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ru/sapteh/model/clients.fxml")));
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Список");
            primaryStage.show();
            pane.getScene().getWindow().hide();
        } else{
            label.setText("Выберите что-нибудь!");
        }
    }

}
