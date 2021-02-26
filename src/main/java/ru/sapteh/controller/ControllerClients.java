package ru.sapteh.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.dom4j.CDATA;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.ClientService;
import ru.sapteh.model.Tag;
import ru.sapteh.service.ServiceClient;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

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
    private TableColumn<Client, String> columnLastRegistrationDate;
    @FXML
    private TableColumn<Client, Integer> columnCountDate;
    @FXML
    private TableColumn<Client, String> columnTag;

    @FXML
    private TextField txtSearch;

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
        searchInit(clientObservableList);
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
        columnLastRegistrationDate.setCellValueFactory(u -> {
            Set<ClientService> clientServiceSet = u.getValue().getClientService();
            if(clientServiceSet.size() == 0){

                return new SimpleObjectProperty<>("Не регистрировался!");
            } else {
                Date lastDate = clientServiceSet.stream().max(Comparator.comparing(ClientService::getStartTime)).get().getStartTime();
                return new SimpleObjectProperty<>(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(lastDate));
            }
        });
        columnCountDate.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getClientService().size()));
        columnTag.setCellValueFactory(u ->{
            Set<Tag> tagSet = u.getValue().getTags();
            if (!tagSet.isEmpty()){
                return new SimpleObjectProperty<>(tagSet.iterator().next().getTitle());
            }
            return new SimpleObjectProperty<>("Без тега!");
        });
        initCellsColor();
        table.setItems(clientObservableList);
        pagination.setPageCount(1);
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
            comboBox.setValue(sizeList);
            comboBoxInit();
            return;
        }
        initCellsColor();
        pagination.setPageCount((int) (Math.ceil((double) sizeList / newValue)));
        pagination.setCurrentPageIndex(0);
        table.setItems(FXCollections.observableArrayList(clientObservableList.subList(pagination.getCurrentPageIndex(), newValue)));
        searchInit(clientObservableList);
        pagination.currentPageIndexProperty().addListener((this::paginationComboBox));
    }

    private void paginationComboBox(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
        if(pagination.getCurrentPageIndex() + 1 == pagination.getPageCount()){
            ObservableList<Client> observableList = FXCollections.observableArrayList(clientObservableList.subList(
                    comboBoxValue * (newNumber.intValue() + 1) - comboBoxValue,
                    sizeList));
            table.setItems(observableList);
            searchInit(observableList);
        }else{
            ObservableList<Client> observableList = FXCollections.observableArrayList(clientObservableList.subList(
                    comboBoxValue * (newNumber.intValue() + 1) - comboBoxValue,
                    comboBoxValue * (newNumber.intValue() + 1)));
            table.setItems(observableList);
            searchInit(observableList);

        }
        initCellsColor();
    }

    private void initCellsColor(){
        columnLastRegistrationDate.setCellFactory(clientStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                if(item != null || !empty){
                    if(item.equalsIgnoreCase("Не регистрировался!")) {
                        setStyle("-fx-background-color: #FF9F93;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    } else {
                        setStyle("-fx-background-color: #fff;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    }
                    setText(item);
                }
            }
        });
        columnTag.setCellFactory(clientStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                if(item != null || !empty){
                    if(item.equalsIgnoreCase("В процессе...")) {
                        setStyle("-fx-background-color: #FFB14D;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    } else if (item.equalsIgnoreCase("Не выполнен.")){
                        setStyle("-fx-background-color: #FF9F93;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    } else if (item.equalsIgnoreCase("Выполнен!")){
                        setStyle("-fx-background-color: #50FF65;" +
                                "-fx-text-fill: #333;" +
                                "-fx-border-color: #eee;");

                    } else {
                        setStyle("-fx-background-color: #fff;" +
                                "-fx-text-fill: #333;" +
                                "-fx-border-color: #eee;");
                    }
                    setText(item);
                }
            }
        });
    }

    private void searchInit(ObservableList<Client> observableList){
        FilteredList<Client> filteredList = new FilteredList<>(observableList, client -> true);
        txtSearch.textProperty().addListener((observableValue, name, value) -> {
            filteredList.setPredicate(client -> {
                if (value == null || value.isEmpty()) {
                    initCellsColor();
                    return true;
                }
                String lowerCaseFilter = value.toLowerCase();

                if (String.valueOf(client.getFirstName()).toLowerCase().contains(lowerCaseFilter)) {
                    initCellsColor();
                    return true;
                } else if (String.valueOf(client.getLastName()).toLowerCase().contains(lowerCaseFilter)) {
                    initCellsColor();
                    return true;
                }
                return false;
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

}
