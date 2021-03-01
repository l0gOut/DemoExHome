package ru.sapteh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Roles;
import ru.sapteh.model.Users;
import ru.sapteh.service.ServiceRoles;
import ru.sapteh.service.ServiceUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerCreateUsers {

    private final ObservableList<Roles> rolesList = FXCollections.observableArrayList();
    private List<Users> loginList = new ArrayList<>();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldLogin;
    @FXML
    private TextField fieldPassword;
    @FXML
    private Label status;

    @FXML
    private ComboBox<Roles> comboBoxRole;

    @FXML
    private void initialize(){
        initRole();
    }

    @FXML
    private void create(){
        String name = fieldName.getText();
        String login = fieldLogin.getText();
        String password = fieldPassword.getText();
        boolean flag = true;
        if(!name.isEmpty() && !login.isEmpty() && !password.isEmpty() && !comboBoxRole.getItems().isEmpty()){
            for (Users loginName : loginList) {
                if (loginName.getLogin().equals(login)){
                    flag = false;
                    status.setText("Логин уже существует!");
                    break;
                }
            }
            if (flag) {
                SessionFactory factory = new Configuration().configure().buildSessionFactory();
                DAO<Users, Integer> serviceUsers = new ServiceUsers(factory);
                serviceUsers.create(new Users(name, login, password, comboBoxRole.getValue()));
                factory.close();
                status.setText("Пользователь зарегестрирован!");
            }
        }
    }

    @FXML
    private void exit(){
        anchorPane.getScene().getWindow().hide();
    }

    private void initRole(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<Roles, Integer> serviceRoles = new ServiceRoles(factory);
        DAO<Users, Integer> serviceUsers = new ServiceUsers(factory);
        rolesList.addAll(serviceRoles.readAll());
        loginList.addAll(serviceUsers.readAll());
        comboBoxRole.setItems(rolesList);
        factory.close();
    }

}
