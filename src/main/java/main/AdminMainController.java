package main;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import main.entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {
    TemporayUser temporayUser= new TemporayUser();
    User user=temporayUser.getCurrentUser();
    private void  userType() throws IOException {
        if (user!=null||!user.getTypeUser().equals("admin")){
                App.setRoot("mainSceneShop");
        }
    }

    public void changeToAllOrder(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminOrder");
    }

    public void changeToAllUser(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminUsers");
    }

    public void changeToAllProduct(MouseEvent mouseEvent) throws IOException {
            App.setRoot("AdminProduct");
        }

    public void changeToAdminSetting(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminSetting");
    }

    public void changeToMainSceneShop(MouseEvent mouseEvent) throws IOException {
        App.setRoot("mainSceneShop");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userType();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
