package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.Product;
import main.entity.ProductReposytory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product,String> type;
    @FXML
    public TableColumn<Product,String> model;
    @FXML
    public TableColumn<Product,String> producer;
    @FXML
    public TableColumn<Product,String> info;
    @FXML
    public TableColumn<Product, Integer> quantity;
    @FXML
    public TableColumn<Product,String> price;

    ProductReposytory productReposytory=new ProductReposytory();
    List<Product> products=productReposytory.getAllProductType(typeQuery);
    ObservableList<Product> productObservableList;
    private static String typeQuery;
    public void setTypeQurt(String type){
        typeQuery=type;
    }

    private void loadDateToTableView() {

        productObservableList = FXCollections.observableArrayList();
        productObservableList.removeAll(productObservableList);
        productObservableList.addAll(products);

        /*Sprawdzanie czy dane się ładują
        for (Product product : productObservableList) {
            System.out.println(product.toString());
        }*/
        tableView.getItems().addAll(productObservableList);
    }
    private void initializeColumn(){
        type.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        model.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        producer.setCellValueFactory(new PropertyValueFactory<Product, String>("producer"));
        info.setCellValueFactory(new PropertyValueFactory<Product,String>("info"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));
    }
    @FXML
    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("mainSceneShop");
    }

    List<Product> productBracket =new ArrayList<>();
    public void addToCurrentBasket(MouseEvent mouseEvent) {
        Product  selectedProduct=tableView.getSelectionModel().getSelectedItem();
        Alert a =new Alert(Alert.AlertType.WARNING);
        boolean duplicateProduct=false;
        if (selectedProduct==null){
            a.setHeaderText("Nic nie zaznaczono");
            a.show();
        }else {
            for (Product p:productBracket){
                if (selectedProduct.equals(p)){
                    duplicateProduct=true;
                }
            }
            if(duplicateProduct==false) {
                if (selectedProduct.getQuantity() != 0) {
                    System.out.println("-->" + selectedProduct.toString() + "-->Bracket");
                    TemporaryBracket bracket = new TemporaryBracket();
                    int currentQuntity = selectedProduct.getQuantity();
                    //selectedProduct.setQuantity(1);
                    productBracket.add(selectedProduct);
                    bracket.setProducts(productBracket);
                    System.out.println("--->TemporaryBracket");
                    selectedProduct.setQuantity(currentQuntity-1);
                    bracket.printsTemporaryBracket();

                } else {
                    a.setHeaderText("Brak Pozycji na magazynie");
                    a.show();
                }
            }
            else{
                a.setHeaderText("Produkt jest już w koszyku");
                a.show();

            }
        }
        tableView.refresh();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateToTableView();

    }
}
