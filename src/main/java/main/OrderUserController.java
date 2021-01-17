package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderUserController implements Initializable {

    public TableView<Order> tableOrderUser;
    public TableColumn<Order, Date> dateColumn;
    public TableColumn<Order, String> statusColumn;
    public TableView<OrderItem> tableOrderItem;
    public TableColumn<OrderItem, Integer> quantity;
    public TableView<Product> productTable;
    public TableColumn<Product,String> producer;
    public TableColumn<Product,String> model;
    public TableColumn<Product,Integer> price;
    public TextField currentPriceOrder;


    public void back(MouseEvent mouseEvent) throws IOException {
        App.setRoot("UserSetting");
    }

    TemporayUser temporayUser = new TemporayUser();
    User user = temporayUser.getCurrentUser();

    OrderRepository orderRepository = new OrderRepository();
    List<Order> orders = orderRepository.getAllOrderOfOneUser(user.getId());
    ObservableList<Order> orderObservableList;

    public void loadDateOrder() {
        if (orders != null) {
            orderObservableList = FXCollections.observableArrayList();
            orderObservableList.removeAll(orderObservableList);
            orderObservableList.addAll(orders);
            tableOrderUser.getItems().addAll(orderObservableList);
        }
    }

    private void initializeColumnOrder() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumnOrder();
        loadDateOrder();
        initializeColumnOrderItem();
        initializeColumnProduct();

    }


    public void showDetails(MouseEvent mouseEvent) {
        Order order = tableOrderUser.getSelectionModel().getSelectedItem();
        loadDateOrderItem(order.getIdOrder());
        loadDateProduct();

    }

    OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
    List<OrderItem> orderItems;
    ObservableList<OrderItem> orderItemObservableList;

    public void loadDateOrderItem(int id_order) {
        orderItems = orderItemReposytory.getAllOrderItemsOnOneOrder(id_order);
        if (orderItems != null) {
            tableOrderItem.getItems().clear();
            orderItemObservableList = FXCollections.observableArrayList();
            orderItemObservableList.removeAll(orderItemObservableList);
            //productTable.refresh();
            orderItemObservableList.addAll(orderItems);
            tableOrderItem.getItems().addAll(orderItemObservableList);

        }
    }

    public void initializeColumnOrderItem() {
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    ObservableList<Product> productObservableList;

    public void loadDateProduct() {
        ProductReposytory productReposytory = new ProductReposytory();
        int count=0;
        int quntity=0;
        if (orderItems != null) {
            productTable.getItems().clear();
            productObservableList=FXCollections.observableArrayList();
            productObservableList.removeAll(productObservableList);
            //productTable.refresh();
            for (OrderItem orderItem : orderItems) {
                productObservableList.add(productReposytory.getOneProduct(orderItem.getProduct().getProductId()));
            }
            for (OrderItem orderItem : orderItems) {
                quntity=orderItem.getQuantity();
                for(Product product:productObservableList){
                    if(product.getProductId()==orderItem.getProduct().getProductId()){
                        count=count+(quntity*product.getPrice());
                    }
                }
            }
            System.out.println("Cena zamówienia->>" +count);
            currentPriceOrder.setText(count+" zł");
            productTable.getItems().addAll(productObservableList);

        }
    }
    public void initializeColumnProduct() {
        producer.setCellValueFactory(new PropertyValueFactory<>("producer"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}