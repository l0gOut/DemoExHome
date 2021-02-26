package ru.sapteh.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.Gender;
import ru.sapteh.service.ServiceClient;

import java.util.Date;

public class ControllerClients {

    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, Integer> columnId;
    @FXML
    private TableColumn<Client, String> columnFirstName;
    @FXML
    private TableColumn<Client, String> columnLastName;
    @FXML
    private TableColumn<Client, String> columnPatronymic;
    @FXML
    private TableColumn<Client, Date> columnBirthday;
    @FXML
    private TableColumn<Client, Date> columnRegistrationDate;
    @FXML
    private TableColumn<Client, String> columnEmail;
    @FXML
    private TableColumn<Client, String> columnPhone;
    @FXML
    private TableColumn<Client, String> columnGender;
    @FXML
    private TableColumn<Client, Date> columnLastRegistrationDate;
    @FXML
    private TableColumn<Client, Integer> columnCountDate;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private Pagination pagination;

    private int sizeList;
    private int comboBoxValue;



    @FXML
    private void initialize() {

        logicInit();

    }


    private void logicInit() {
        initDateBase();
        tableInitFirstView();
        comboBoxInit();
    }

    private void initDateBase() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<Client, Integer> serviceClient = new ServiceClient(factory);
        clientObservableList.addAll(serviceClient.readAll());
    }

    private void tableInitFirstView() {
        columnId.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getId()));
        columnFirstName.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getFirstName()));
        columnLastName.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLastName()));
        columnPatronymic.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getPatronymic()));
        columnBirthday.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getBirthday()));
        columnRegistrationDate.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getRegistrationDate()));
        columnEmail.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getEmail()));
        columnPhone.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getPhoneNumber()));
        columnGender.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getGender().getName()));
//        columnPhotoPath.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getPhotoPath()));
        table.setItems(clientObservableList);
    }

    private void comboBoxInit() {
        sizeList = clientObservableList.size();
        comboBox.setItems(FXCollections.observableArrayList(10, 50, 200, sizeList));
        comboBox.setValue(sizeList);
        comboBox.valueProperty().addListener((this::comboBoxLogic));
    }

    private void comboBoxLogic(ObservableValue<? extends Integer> observableList, Integer oldValue, Integer newValue) {
        comboBoxValue = comboBox.getValue();
        if (comboBoxValue > sizeList) {
            comboBoxValue = sizeList;
            newValue = sizeList;
        }
        pagination.setPageCount((int) (Math.ceil((double) sizeList / newValue)));
        pagination.setCurrentPageIndex(0);
        table.setItems(FXCollections.observableArrayList(clientObservableList.subList(pagination.getCurrentPageIndex(), newValue)));
        pagination.currentPageIndexProperty().addListener((this::paginationComboBox));
    }

    private void paginationComboBox(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
        if(pagination.getCurrentPageIndex() + 1 == pagination.getPageCount()){
            table.setItems(FXCollections.observableArrayList(clientObservableList.subList(
                    comboBoxValue * (newNumber.intValue() + 1) - comboBoxValue,
                    sizeList)));
        }else{
            table.setItems(FXCollections.observableArrayList(clientObservableList.subList(
                    comboBoxValue * (newNumber.intValue() + 1) - comboBoxValue,
                    comboBoxValue * (newNumber.intValue() + 1))));
        }
    }

}
