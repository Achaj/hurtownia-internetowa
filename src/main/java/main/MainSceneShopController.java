package main;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import main.entity.ProductReposytory;
import main.entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneShopController  implements Initializable{

    public void cpuSceneChange(MouseEvent mouseEvent) throws IOException {
        ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("cpu");
        App.setRoot("Product");

    }

    public void gpuSceneChange(MouseEvent mouseEvent) throws IOException{
        ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("gpu");
        App.setRoot("Product");
    }

    public void motherboardSceneChange(MouseEvent mouseEvent) {
    }

    public void harddriveSceneChange(MouseEvent mouseEvent) {
    }

    public void psuSceneChange(MouseEvent mouseEvent) {
    }

    public void cddriverSceneChange(MouseEvent mouseEvent) {
    }

    public void computercaseSceneChange(MouseEvent mouseEvent) {
    }

    public void soundSceneChange(MouseEvent mouseEvent) {
    }

    public void coolerSceneChange(MouseEvent mouseEvent) {
    }
    public void setSceneLogin(MouseEvent mouseEvent) throws IOException {
        TemporayUser temporayUser=new TemporayUser();
        User user=temporayUser.getCurrentUser();
        if(user==null){
            App.setRoot("loginScene");
        }else {
            App.setRoot("UserSetting");
        }
    }

    public void setSceneBasket(MouseEvent mouseEvent) throws IOException {
        App.setRoot("Bracket");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
