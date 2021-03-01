package ru.sapteh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.Gender;
import ru.sapteh.service.ServiceClient;
import ru.sapteh.service.ServiceGender;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ControllerCreateClient {

    private final ObservableList<Gender> listGender = FXCollections.observableArrayList();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldPatronymic;
    @FXML
    private TextField fieldLastName;
    @FXML
    private TextField fieldEmail;
    @FXML
    private TextField fieldPhone;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<Gender>  comboBoxGender;

    @FXML
    private Label status;


    @FXML
    private void initialize(){
        initGender();
    }

    @FXML
    private void create(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<Client, Integer> serviceClient = new ServiceClient(factory);
        serviceClient.create(new Client(
                comboBoxGender.getValue(),fieldFirstName.getText(),fieldLastName.getText(),fieldPatronymic.getText(),
                Date.from(birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), fieldPhone.getText(),
                fieldEmail.getText(), Calendar.getInstance().getTime()
        ));
        status.setText("Клиент добавлен!");
        factory.close();
    }

    @FXML
    private void exit(){
        anchorPane.getScene().getWindow().hide();
    }

    private void initGender(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<Gender, Character> serviceGender = new ServiceGender(factory);
        listGender.addAll(serviceGender.readAll());
        comboBoxGender.setItems(listGender);
        factory.close();
    }
}
