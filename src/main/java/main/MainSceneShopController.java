package main;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.entity.ProductReposytory;
import main.entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneShopController  implements Initializable{

    public Button backAdminPanel;
    public TextField serchingModel;

    public void cpuSceneChange(MouseEvent mouseEvent) throws IOException {
       // ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("cpu");
        App.setRoot("Product");

    }

    public void gpuSceneChange(MouseEvent mouseEvent) throws IOException{
       // ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("gpu");
        App.setRoot("Product");
    }

    public void motherboardSceneChange(MouseEvent mouseEvent) throws IOException {
      //  ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("motherboard");
        App.setRoot("Product");
    }

    public void harddriveSceneChange(MouseEvent mouseEvent) throws IOException {
       // ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("disk");
        App.setRoot("Product");
    }

    public void psuSceneChange(MouseEvent mouseEvent) throws IOException {
      //  ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("psu");
        App.setRoot("Product");
    }

    public void cddriverSceneChange(MouseEvent mouseEvent) throws IOException {
     //   ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("cddriver");
        App.setRoot("Product");
    }

    public void computercaseSceneChange(MouseEvent mouseEvent) throws IOException {
     //   ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("case");
        App.setRoot("Product");
    }

    public void soundSceneChange(MouseEvent mouseEvent) throws IOException {
       // ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("soundcard");
        App.setRoot("Product");
    }

    public void coolerSceneChange(MouseEvent mouseEvent) throws IOException {
       // ProductReposytory productReposytory=new ProductReposytory();
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setTypeQurt("ram");
        App.setRoot("Product");
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
      if(user!=null){
          try {
              userType();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    }

    public void backAdminPanel(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminMainScene");
    }
    TemporayUser temporayUser= new TemporayUser();
    User user=temporayUser.getCurrentUser();
    private void  userType() throws IOException {
        if (user.getTypeUser().equals("admin")){
            backAdminPanel.setVisible(true);
            backAdminPanel.setDisable(false);
        }
    }

    public void searchModel(MouseEvent mouseEvent) throws IOException {
        ProductController centralProfessorUnitControllers=new ProductController();
        centralProfessorUnitControllers.setNameQuery(serchingModel.getText());
        App.setRoot("Product");
    }
}
