package ru.sapteh;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.Configurable;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.ClientService;
import ru.sapteh.service.ServiceClientService;

import java.util.Objects;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ru/sapteh/model/select.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Выберите сколько нужно отображать пользователей");
        primaryStage.show();
    }

    public static void main(String[] args) {
//        launch(args);
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<ClientService, Integer> clientService = new ServiceClientService(factory);
        for (ClientService client: clientService.readAll()) {
            System.out.println(client);
        }
    }
}
