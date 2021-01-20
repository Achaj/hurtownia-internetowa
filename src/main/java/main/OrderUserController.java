package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    ObservableList<Order> orderObservableList;

    public void loadDateOrder() {
        OrderRepository orderRepository = new OrderRepository();
        List<Order> orders = orderRepository.getAllOrderOfOneUser(user.getIdUser());
        if (orders != null) {
            orderObservableList = FXCollections.observableArrayList();
            orderObservableList.removeAll(orderObservableList);
            orderObservableList.addAll(orders);
            tableOrderUser.getItems().addAll(orderObservableList);
        }
        orderRepository.closeConnectDB();
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


    List<OrderItem> orderItems;
    ObservableList<OrderItem> orderItemObservableList;

    public void loadDateOrderItem(int id_order) {
        OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
        orderItems = orderItemReposytory.getAllOrderItemsOnOneOrder(id_order);
        if (orderItems != null) {
            tableOrderItem.getItems().clear();
            orderItemObservableList = FXCollections.observableArrayList();
            orderItemObservableList.removeAll(orderItemObservableList);
            orderItemObservableList.addAll(orderItems);
            tableOrderItem.getItems().addAll(orderItemObservableList);

        }
        orderItemReposytory.closeConnectDB();
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
                productObservableList.add(productReposytory.getOneProduct(orderItem.getProduct().getIdProduct()));
            }
            for (OrderItem orderItem : orderItems) {
                quntity=orderItem.getQuantity();
                for(Product product:productObservableList){
                    if(product.getIdProduct()==orderItem.getProduct().getIdProduct()){
                        count=count+(quntity*product.getPrice());
                    }
                }
            }
            System.out.println("Cena zamówienia->>" +count);
            currentPriceOrder.setText(count+" zł");
            productTable.getItems().addAll(productObservableList);

        }
        productReposytory.closeConnectDB();
    }
    public void initializeColumnProduct() {
        producer.setCellValueFactory(new PropertyValueFactory<>("producer"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void canelOrder(MouseEvent mouseEvent) {
        //pobraranie zaznaczonego zamówienia
        Order order = tableOrderUser.getSelectionModel().getSelectedItem();
        //sprawdzanie czy zamówienie nie zostało już anulowane
        if (!order.getStatus().equals("Anulowano")) {
            ProductReposytory productReposytory = new ProductReposytory();
            OrderRepository orderRepository = new OrderRepository();
            orderRepository.updateOrderStatus(order.getIdOrder(), "Anulowano");
            int quntity = 0;
            for (OrderItem orderItem : orderItems) {
                quntity = orderItem.getQuantity();
                for (Product product : productObservableList) {
                    if (product.getIdProduct() == orderItem.getProduct().getIdProduct()) {
                        Product productINdB = productReposytory.getOneProduct(product.getIdProduct());
                        productReposytory.updateProductQuantity(product.getIdProduct(), productINdB.getQuantity() + quntity);
                    }
                }
                productReposytory.closeConnectDB();
                orderRepository.closeConnectDB();
            }
            orderObservableList.clear();
            loadDateOrder();
            tableOrderUser.refresh();
        }else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Zamówienie już jest anulowane");
            alert.show();
        }
    }
}