package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.OrderItem;
import main.entity.OrderItemReposytory;
import main.entity.Product;
import main.entity.ProductReposytory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminProductController implements Initializable {
    public TableView<Product> tableProduct;
    public TableColumn<Product,Integer> idProductTable;
    public TableColumn<Product,String>  type;
    public TableColumn<Product,String>  producer;
    public TableColumn<Product,String> model;
    public TableColumn<Product,Integer> quantity;
    public TableColumn<Product,Integer> price;
    public TextField idProductEdit;
    public TextField typeProductEdit;
    public TextField producentProductEdit;
    public TextField modelProductEdit;
    public TextField quantityProductEdit;
    public TextField priceProductEdit;
    public TextArea infoProductEdit;

    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminMainScene");
    }

    public void showProduct(MouseEvent mouseEvent) {
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        if(product!=null){
            idProductEdit.setText(String.valueOf(product.getIdProduct()));
            typeProductEdit.setText(product.getType());
            producentProductEdit.setText(product.getProducer());
            modelProductEdit.setText(product.getModel());
            quantityProductEdit.setText(String.valueOf(product.getQuantity()));
            priceProductEdit.setText(String.valueOf(product.getPrice()));
            infoProductEdit.setText(product.getInfo());

        }
    }
    ObservableList<Product> productObservableList;
    private void initializeColumn(){
        idProductTable.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        producer.setCellValueFactory(new PropertyValueFactory<>("producer"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    private void loadDateProduct() {
        tableProduct.getItems().clear();
        ProductReposytory productReposytory = new ProductReposytory();
        List<Product> productsModel = productReposytory.getAllProduct();
        productReposytory.closeConnectDB();
        if (productsModel.size() != 0) {
            productObservableList = FXCollections.observableArrayList();
            productObservableList.removeAll(productObservableList);
            productObservableList.addAll(productsModel);
            tableProduct.getItems().addAll(productObservableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Baza produktów jest pusta");
            alert.show();

        }
    }
            @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         initializeColumn();
         loadDateProduct();
    }

    public void changePrice(MouseEvent mouseEvent) {
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        if(product!=null) {
            ProductReposytory  productReposytory=new ProductReposytory();
            productReposytory.updateProductPrice(product.getIdProduct(), Integer.parseInt(priceProductEdit.getText()));
            productReposytory.closeConnectDB();
            loadDateProduct();
        }
    }

    public void changeQuantity(MouseEvent mouseEvent) {
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        if(product!=null) {
            ProductReposytory  productReposytory=new ProductReposytory();
            productReposytory.updateProductQuantity(product.getIdProduct(), Integer.parseInt(quantityProductEdit.getText()));
            productReposytory.closeConnectDB();
            loadDateProduct();
        }
    }

    public void addProduct(MouseEvent mouseEvent) {
        if(infoProductEdit!=null&&typeProductEdit!=null&&priceProductEdit!=null&&producentProductEdit!=null
        &&modelProductEdit!=null&quantityProductEdit!=null) {
            Product product=new Product();
            product.setType(typeProductEdit.getText());
            product.setProducer(producentProductEdit.getText());
            product.setModel(modelProductEdit.getText());
            product.setQuantity(Integer.parseInt(quantityProductEdit.getText()));
            product.setPrice(Integer.parseInt(priceProductEdit.getText()));
            product.setInfo(infoProductEdit.getText());
            ProductReposytory  productReposytory=new ProductReposytory();
            productReposytory.saveProduct(product);
            productReposytory.closeConnectDB();
            loadDateProduct();
        }
    }

    public void delateProduct(MouseEvent mouseEvent) {
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        if(product!=null) {
            ProductReposytory productReposytory = new ProductReposytory();
            OrderItemReposytory orderItemReposytory=new OrderItemReposytory();
            List<OrderItem> orderItems=orderItemReposytory.getAllOrderItemsAllOrders();
            if(orderItems!=null){
                for(OrderItem item:orderItems){
                    if(product.getIdProduct()==item.getProduct().getIdProduct()) {
                        orderItemReposytory.updateProductOnOrderId(item.getIdOrderItem(), null);
                    }
                }
            }
            orderItemReposytory.closeConnectDB();
            if(!productReposytory.delateProduct(product.getIdProduct())){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Produkt o takim id jest zawart w zamówieniu");
                alert.setContentText("Usuń pozycję z zamówienia aby usunąc produkt");
                alert.show();
            }
            productReposytory.closeConnectDB();
            loadDateProduct();
        }
    }

    public void changeInfo(MouseEvent mouseEvent) {
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        if(product!=null) {
            ProductReposytory  productReposytory=new ProductReposytory();
            productReposytory.updateProductInfo(product.getIdProduct(), infoProductEdit.getText());
            productReposytory.closeConnectDB();
            loadDateProduct();
        }
    }

    public void clear(MouseEvent mouseEvent) {
        idProductEdit.setText("");
        typeProductEdit.setText("");
        producentProductEdit.setText("");
        modelProductEdit.setText("");
        quantityProductEdit.setText("");
        priceProductEdit.setText("");
        infoProductEdit.setText("");
    }
}
