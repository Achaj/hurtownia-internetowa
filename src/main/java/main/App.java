package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.entity.User;
import main.entity.UserRepository;


import java.io.IOException;

import static javax.persistence.Persistence.createEntityManagerFactory;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainSceneShop"), 800, 600);
        stage.setScene(scene);
        stage.show();
        UserRepository userRepository = new UserRepository();
        User user = new User("janeko0@op.pl", "pass", "jan", "jan", "11-111", "mazury", "-", "1b","1111111111");
        userRepository.saveUser(user);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    


}