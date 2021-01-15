package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.entity.Product;
import main.entity.ProductReposytory;

import java.sql.ResultSet;
import java.util.List;

public class centralProfessorUnitControllers {
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
    List<Product> products=productReposytory.getAllProductType("cpu");
    ObservableList<Product> productObservableList;



    private void loadDateToTableView() {

        productObservableList = FXCollections.observableArrayList();
        productObservableList.removeAll(productObservableList);
        productObservableList.addAll(products);
        for (Product product : productObservableList) {
            System.out.println(product.toString());
        }
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

    public void initialize(){
        initializeColumn();
        loadDateToTableView();
    }
}
