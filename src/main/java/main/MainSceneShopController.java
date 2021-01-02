package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import main.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneShopController implements Initializable {
    @FXML
    private ComboBox<String> comboBoxCategoryItem;

    private final String[] COMPUTERS_COMPONENT={"Procesor","Karta Graficzna","Zasilacz","Płyta Główna","Dysk","Karta Dziękowa","Napęd CD","Obudowa","Chłodzenie"};
    private  final ObservableList<String> COMPONENT_LIST= FXCollections.observableArrayList(COMPUTERS_COMPONENT);


    public void cpuSceneChange(MouseEvent mouseEvent) throws IOException {
        App.setRoot("centralProcesorUnitScene");
    }

    public void gpuSceneChange(MouseEvent mouseEvent) {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCategoryItem.setItems(COMPONENT_LIST);
    }

    public void setSceneLogin(MouseEvent mouseEvent) throws IOException {
        App.setRoot("loginScene");
    }

    public void setSceneBasket(MouseEvent mouseEvent) {
    }
}
