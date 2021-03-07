package ru.sapteh.controller;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Client;
import ru.sapteh.model.ClientService;
import ru.sapteh.model.Tag;
import ru.sapteh.service.ServiceClient;
import ru.sapteh.service.ServiceTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

public class ControllerClients {

    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    private final ObservableList<Tag> tagObservableList = FXCollections.observableArrayList();

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

    @FXML
    private Button visibleButtonUsers;
    @FXML
    private Button visibleButtonClient;

    private int sizeList;
    private int comboBoxValue;

    @FXML
    private void initialize() {
        if (!ControllerEntrance.active){
            visibleButtonUsers.setVisible(false);
            visibleButtonClient.setVisible(false);
        }
        logicInit();
    }

    @FXML
    private void createUsers() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ru/sapteh/model/createUsers.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Создание Пользователя");
        stage.showAndWait();
    }
    @FXML
    private void createClient() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ru/sapteh/model/createClients.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Создание Клиента");
        stage.showAndWait();
    }

    @FXML
    private void saveToPDF() throws Exception {
        String fileName = "test.pdf";
        Document document = new Document(PageSize.A4.rotate());  //landscape orientation
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        //add image in pdf
        Image image = Image.getInstance("C:\\Users\\Администратор\\IdeaProjects\\DemoExHome\\Предварительный демоэкзамен\\Screenshot_1.png");
        image.scaleAbsoluteHeight(100);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);

        //add paragraph
        String FONT = "./src/main/resources/font/arial.ttf";

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf,10,Font.NORMAL);

        Paragraph paragraph = new Paragraph("Клиенты автосервиса", new Font(bf, 30, Font.NORMAL));
        paragraph.setSpacingAfter(20);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        //--------------------------table--------------------------------
        //получить количество столбцов
        int numColumns = table.getColumns().size();
        PdfPTable tables = new PdfPTable(numColumns);
        //получить имена столбцов
        ObservableList<TableColumn<Client, ?>> columns = table.getColumns();

        for(TableColumn<Client, ?> column : columns){
            tables.addCell(new PdfPCell(new Phrase(column.getText(), new Font(bf, 14, Font.NORMAL))));
        }
        tables.setHeaderRows(1);

        //test
        for (Client client: clientObservableList) {
            tables.addCell(new PdfPCell(new Phrase(String.valueOf(client.getId()), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getFirstName(), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getLastName(), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getPatronymic(), font)));
            tables.addCell(new PdfPCell(new Phrase(String.valueOf(client.getBirthday()), font)));
            tables.addCell(new PdfPCell(new Phrase(String.valueOf(client.getRegistrationDate()), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getEmail(), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getPhoneNumber(), font)));
            tables.addCell(new PdfPCell(new Phrase(client.getGender().getName(), font)));
            tables.addCell(new PdfPCell(new Phrase("1", font)));
            tables.addCell(new PdfPCell(new Phrase(String.valueOf(client.getClientService().size()), font)));
            tables.addCell(new PdfPCell(new Phrase("Без тега")));
        }
        document.add(tables);

        document.close();
        System.out.println("finished");
    }

    @FXML
    private void saveToExcel() throws Exception {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выберите путь");
        File file=fileChooser.showSaveDialog(new Stage());
        String fileName= file.getAbsolutePath();
        XSSFWorkbook workbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);

        Sheet sheet = workbook.createSheet("Client");
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)14);
        headerStyle.setFont(font);
        //Header cell
        ObservableList<TableColumn<Client, ?>> columns = table.getColumns();
        int count = 0;
        for(TableColumn<Client, ?> column : columns){
            Cell headerCell = header.createCell(count++);
            headerCell.setCellValue(column.getText());
            headerCell.setCellStyle(headerStyle);
        }

        //Next cell (tableViewClient.getItems())
        for (int i = 0; i < table.getItems().size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell id = row.createCell(0);
            Cell gender = row.createCell(8);
            Cell lastName = row.createCell(2);
            Cell firstName = row.createCell(1);
            Cell patronymic = row.createCell(3);
            Cell birthday = row.createCell(4);
            Cell phone = row.createCell(7);
            Cell email = row.createCell(6);
            Cell dateRegistration = row.createCell(5);
            Cell lastDateVisit = row.createCell(9);
            Cell quantityVisit = row.createCell(10);
            id.setCellValue(table.getItems().get(i).getId());
            gender.setCellValue(String.valueOf(table.getItems().get(i).getGender().getCode()));
            firstName.setCellValue(table.getItems().get(i).getFirstName());
            lastName.setCellValue(table.getItems().get(i).getLastName());
            patronymic.setCellValue(table.getItems().get(i).getPatronymic());
            birthday.setCellValue(table.getItems().get(i).getBirthday().toString());
            dateRegistration.setCellValue(table.getItems().get(i).getRegistrationDate().toString());
            email.setCellValue(table.getItems().get(i).getEmail());
            phone.setCellValue(table.getItems().get(i).getPhoneNumber());
            quantityVisit.setCellValue(table.getItems().get(i).getClientService().size());
            Set<ClientService> services = table.getItems().get(i).getClientService();
            if (services.size() != 0) {
                lastDateVisit.setCellValue(services.stream().max(Comparator.comparing(ClientService::getStartTime)).get().getStartTime().toString());
            }
        }
        workbook.write(new FileOutputStream(fileName));
        workbook.close();
        System.out.println("finished");
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
        DAO<Tag,Integer> serviceTag = new ServiceTag(factory);
        clientObservableList.addAll(serviceClient.readAll());
        tagObservableList.addAll(serviceTag.readAll());
        factory.close();
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
        table.setEditable(true);
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
        searchInit(table.getItems());
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
                    assert item != null;
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
                    assert item != null;
                    if(item.equalsIgnoreCase(tagObservableList.get(2).getTitle())) {
                        setStyle("-fx-background-color: #FFB14D;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    } else if (item.equalsIgnoreCase(tagObservableList.get(1).getTitle())){
                        setStyle("-fx-background-color: #FF9F93;" +
                                "-fx-text-fill: #333;"+
                                "-fx-border-color: #eee;");
                    } else if (item.equalsIgnoreCase(tagObservableList.get(0).getTitle())){
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
        txtSearch.textProperty().addListener((observableValue, name, value) -> filteredList.setPredicate(client -> {
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
            } else if (String.valueOf(client.getPatronymic()).toLowerCase().contains(lowerCaseFilter)){
                initCellsColor();
                return true;
            }
            return false;
        }));
        SortedList<Client> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

}
