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

public class AdminOrderController  implements Initializable {
    public TableView<Order> tableOrder;
    public TableColumn<Order,Integer> idOrderTable;
    public TableColumn<Order, Date> dateColumn;
    public TableColumn<Order, String> statusColumn;
    public TableColumn<Order,Integer> idUserTable;

    public TableView<OrderItem> tableOrderItem;
    public TableColumn<OrderItem, Integer> quantity;
    public TableColumn<OrderItem,Product> product;
    public TextField currentPriceOrder;



    public void back(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminMainScene");
    }

    TemporayUser temporayUser = new TemporayUser();
    User user = temporayUser.getCurrentUser();
    ObservableList<Order> orderObservableList;

    public void loadDateOrder() {
        OrderRepository orderRepository = new OrderRepository();
        List<Order> orders = orderRepository.getAllOrder();
        if (orders != null) {
            orderObservableList = FXCollections.observableArrayList();
            orderObservableList.removeAll(orderObservableList);
            orderObservableList.addAll(orders);
            tableOrder.getItems().addAll(orderObservableList);
        }
        orderRepository.closeConnectDB();
    }

    private void initializeColumnOrder() {
        idOrderTable.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        idUserTable.setCellValueFactory(new PropertyValueFactory<>("user"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumnOrder();
        loadDateOrder();
        initializeColumnOrderItem();


    }


    public void showDetails(MouseEvent mouseEvent) {
        Order order = tableOrder.getSelectionModel().getSelectedItem();
        loadDateOrderItem(order.getIdOrder());


    }


    List<OrderItem> orderItems;
    ObservableList<OrderItem> orderItemObservableList;

    public void loadDateOrderItem(int id_order) {
        OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
        orderItems = orderItemReposytory.getAllOrderItemsOnOneOrder(id_order);
        orderItemReposytory.closeConnectDB();
        int count=0;
        if (orderItems != null) {
            tableOrderItem.getItems().clear();
            orderItemObservableList = FXCollections.observableArrayList();
            orderItemObservableList.removeAll(orderItemObservableList);
            orderItemObservableList.addAll(orderItems);
            tableOrderItem.getItems().addAll(orderItemObservableList);
            for(OrderItem orderItem:orderItems){
                count=count+(orderItem.getProduct().getPrice()*orderItem.getQuantity());
            }

        }
        currentPriceOrder.setText(count+" zł");

    }

    public void initializeColumnOrderItem() {
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        product.setCellValueFactory(new PropertyValueFactory<>("product"));
    }



    public void canelOrder(MouseEvent mouseEvent) {
        //pobraranie zaznaczonego zamówienia
        Order order = tableOrder.getSelectionModel().getSelectedItem();
        //sprawdzanie czy zamówienie nie zostało już anulowane
        if (!order.getStatus().equals("Anulowano")) {
            ProductReposytory productReposytory = new ProductReposytory();
            OrderRepository orderRepository = new OrderRepository();
            orderRepository.updateOrderStatus(order.getIdOrder(), "Anulowano");

            int quntity = 0;
            if(orderItems.size()!=0) {
                for (OrderItem orderItem : orderItems) {
                    quntity = orderItem.getQuantity();
                    int product_id = orderItem.getProduct().getIdProduct();
                    Product productINdB = productReposytory.getOneProduct(product_id);
                    productReposytory.updateProductQuantity(product_id, productINdB.getQuantity() + quntity);
                }
            }
            orderObservableList.clear();
            loadDateOrder();
            tableOrder.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Zamówienie już jest anulowane");
            alert.show();
        }
    }

    public void dellOrder(MouseEvent mouseEvent) {
        //pobraranie zaznaczonego zamówienia
        Order order = tableOrder.getSelectionModel().getSelectedItem();
            ProductReposytory productReposytory = new ProductReposytory();
            OrderRepository orderRepository = new OrderRepository();
            OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
            int quntity = 0;
            if(!order.getStatus().equals("Anulowano")&&orderItems!=null) {
                for (OrderItem orderItem : orderItems) {
                    quntity = orderItem.getQuantity();
                    int product_id = orderItem.getProduct().getIdProduct();
                    Product productINdB = productReposytory.getOneProduct(product_id);
                    productReposytory.updateProductQuantity(product_id, productINdB.getQuantity() + quntity);
                }
            }

            List<OrderItem> orderItems=orderItemReposytory.getAllOrderItemsOnOneOrder(order.getIdOrder());
            for (OrderItem item:orderItems){
                orderItemReposytory.delateOrderItemById(item.getIdOrderItem());
            }
            orderRepository.delateOrderById(order.getIdOrder());
            orderItemReposytory.closeConnectDB();
            orderRepository.closeConnectDB();
            productReposytory.closeConnectDB();
            orderItems.clear();
            orderObservableList.clear();
            loadDateOrder();
            tableOrder.refresh();
    }

    public void editOrder(MouseEvent mouseEvent) throws IOException {
        Order order=tableOrder.getSelectionModel().getSelectedItem();
        AdminOrderEditController.setOrder(order);
                App.setRoot("AdminOrderEdit");
    }
}
