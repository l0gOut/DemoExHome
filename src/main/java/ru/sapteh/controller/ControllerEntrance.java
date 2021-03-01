package ru.sapteh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Roles;
import ru.sapteh.model.Users;
import ru.sapteh.service.ServiceUsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerEntrance {

    private final Map<String, String> listUsers = new HashMap<>();
    private final Map<String, String> listRole = new HashMap<>();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField fieldLogin;
    @FXML
    private TextField fieldPassword;
    @FXML
    private Label labelLogin;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelStatus;

    public static boolean active = false;

    @FXML
    private void initialize(){
        listAdd();
    }

    @FXML
    private void join() throws IOException {
        try {
            String login = fieldLogin.getText();
            String password = fieldPassword.getText();
            if (login.isEmpty() && password.isEmpty()){
                labelLogin.setText("Поле пусто!");
                labelPassword.setText("Поле пусто!");
            } else if (login.isEmpty()){
                labelLogin.setText("Поле пусто!");
            } else if(password.isEmpty()){
                labelPassword.setText("Поле пусто");
            } else if (listUsers.get(login).equals(password)) {
                if (listRole.get(login).equals("admin")) {
                    active = true;
                }
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/ru/sapteh/model/clients.fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Бд");
                anchorPane.getScene().getWindow().hide();
                stage.show();
            } else if (!listUsers.get(login).equals(password)){
                labelPassword.setText("Неправильный пароль!");
            }
        } catch (NullPointerException nullPointerException){
            labelLogin.setText("Неправильный логин!");
        }
    }

    @FXML
    private void createUser(){
        try {
            String login = fieldLogin.getText();
            String password = fieldPassword.getText();
            if (login.isEmpty() && password.isEmpty()){
                labelLogin.setText("Поле пусто!");
                labelPassword.setText("Поле пусто!");
            } else if (login.isEmpty()){
                labelLogin.setText("Поле пусто!");
            } else if(password.isEmpty()){
                labelPassword.setText("Поле пусто");
            } else if (listUsers.get(login).equals(password) && listRole.get(login).equals("admin")){

            } else {
                labelStatus.setText("Вы не администратор или же вы где то ошиблись...");
            }
        } catch (NullPointerException nullPointerException){
            labelLogin.setText("Неправильный логин!");
        }
    }

    @FXML
    private void key(){
        labelLogin.setText("");
        labelPassword.setText("");
        labelStatus.setText("");
    }

    private void listAdd(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<Users, Integer> serviceUsers = new ServiceUsers(factory);
        List<Users> listMember = new ArrayList<>(serviceUsers.readAll());
        for (Users users: listMember) {
            listUsers.put(users.getLogin(), users.getPassword());
            listRole.put(users.getLogin(), users.getRole().getRole());
        }
        factory.close();
    }

    private void roleSearch(){

    }
}
