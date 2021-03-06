package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.Product;
import main.entity.ProductReposytory;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    private TextField searingModel;
    ObservableList<Product> productObservableList;
    private static String typeQuery;
    private static String nameQuery;

    public void setTypeQurt(String type){
        typeQuery=type;
    }
    public void setNameQuery(String name){
        nameQuery=name;
    }

    private void loadDateProductType() {
        tableView.getItems().clear();
        ProductReposytory reposytory=new ProductReposytory();
        List<Product> productsType =reposytory.getAllProductType(typeQuery);
        reposytory.closeConnectDB();
        if(productsType.size()!=0) {
            productObservableList = FXCollections.observableArrayList();
            productObservableList.removeAll(productObservableList);
            productObservableList.addAll(productsType);
            tableView.getItems().addAll(productObservableList);
        }else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Nie znaleziono produktów w określonej kategoi ->"+typeQuery);
            alert.show();
        }

    }
    private void loadDateProductModel(){
        tableView.getItems().clear();
        ProductReposytory productReposytory=new ProductReposytory();
        List<Product> productsModel =productReposytory.getAllByProductName(nameQuery);
        productReposytory.closeConnectDB();
        if(productsModel.size()!=0){
            productObservableList = FXCollections.observableArrayList();
            productObservableList.removeAll(productObservableList);
            productObservableList.addAll(productsModel);
            tableView.getItems().addAll(productObservableList);
        }else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Nie znalezion takiego modelu ->"+nameQuery);
            alert.show();
        }
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
    public void addToCurrentBasket(MouseEvent mouseEvent) {
        Product  selectedProduct=tableView.getSelectionModel().getSelectedItem();
        Alert a =new Alert(Alert.AlertType.WARNING);

        if (selectedProduct==null){
            a.setHeaderText("Nic nie zaznaczono");
            a.show();
        }else {
            boolean duplicateProduct=false;
            for (Product p:TemporaryBracket.products){
                if (p.getIdProduct()==selectedProduct.getIdProduct()){
                    duplicateProduct=true;
                }
            }
            if(duplicateProduct==false) {
                if (selectedProduct.getQuantity() != 0) {
                    System.out.println("-->" + selectedProduct.toString() + "-->Bracket");
                    TemporaryBracket bracket = new TemporaryBracket();
                    TemporaryBracket.products.add(selectedProduct);
                    System.out.println("--->TemporaryBracket");
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
        if(typeQuery!=null) {
            loadDateProductType();
        }else if (nameQuery!=null){
            loadDateProductModel();
        }

    }

    public void searchProduct(MouseEvent mouseEvent) {
        if(searingModel!=null) {
            tableView.getItems().clear();
            ProductReposytory productReposytory=new ProductReposytory();
            List<Product> productsModelType =productReposytory.getAllByProductNameAndType(searingModel.getText(),typeQuery);
            productReposytory.closeConnectDB();
            if(productsModelType.size()!=0){
                productObservableList = FXCollections.observableArrayList();
                productObservableList.removeAll(productObservableList);
                productObservableList.addAll(productsModelType);
                tableView.getItems().addAll(productObservableList);
                tableView.refresh();
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie znalezion takiego model w kategori ->"+typeQuery);
                alert.show();
            }
        }
    }
}
