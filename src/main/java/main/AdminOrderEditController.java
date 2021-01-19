package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import main.entity.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminOrderEditController implements Initializable {
    private static Order order;
    public TableView<OrderItem> tableOrderItem;
    public TableColumn<OrderItem,Integer> quantity;
    public TableColumn<OrderItem,String> product;
    public TextField currentPriceOrder;
    public TextField idOrder;
    public TextField idUser;
    public TextField dateOrder;
    public TextField statusOrder;
    public DatePicker newDataOrder;
    public TextField newQuantityOrderItem;
    public ChoiceBox<String> newStatusOrder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateOrder();
        initializeColumnOrderItem();
        loadDateOrderItem(order.getIdOrder());
        loadDateChoiceBox();
    }
    ObservableList<String> statusChoice=FXCollections.observableArrayList();
        private void loadDateChoiceBox(){
            statusChoice.removeAll(statusChoice);
            statusChoice.add("Zamówiono");
            statusChoice.add("W trakcie realizacij");
            statusChoice.add("Wysłano");
            newStatusOrder.getItems().addAll(statusChoice);

        }

    public static void setOrder(Order order) {
        AdminOrderEditController.order = order;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminOrder");
    }

    public void delatePosition(MouseEvent mouseEvent) {

        OrderItem orderItem= tableOrderItem.getSelectionModel().getSelectedItem();
        if (orderItem!=null) {
            OrderItemReposytory orderItemReposytory=new OrderItemReposytory();
            ProductReposytory productReposytory = new ProductReposytory();
            Product product = productReposytory.getOneProduct(orderItem.getProduct().getProductId());
            productReposytory.updateProductQuantity(product.getProductId(), product.getQuantity() + orderItem.getQuantity());
            productReposytory.closeConnectDB();
            orderItemReposytory.delateOrderItemById(orderItem.getIdOrderItem());
            orderItemReposytory.closeConnectDB();
        }

        loadDateOrderItem(order.getIdOrder());
    }

    public void editQuantity(MouseEvent mouseEvent) {
        OrderItemReposytory orderItemReposytory=new OrderItemReposytory();
        OrderItem orderItem= tableOrderItem.getSelectionModel().getSelectedItem();
        ProductReposytory productReposytory=new ProductReposytory();
        Product product=productReposytory.getOneProduct(orderItem.getProduct().getProductId());
        if(product!=null&&orderItem!=null) {
            int currentQuantiy = Integer.parseInt(newQuantityOrderItem.getText());
            if (currentQuantiy > product.getQuantity()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Brak takiej ilości w magazynie");
                alert.show();
            } else if (currentQuantiy < orderItem.getQuantity()) {
                productReposytory.updateProductQuantity(orderItem.getProduct().getProductId(), product.getQuantity() + currentQuantiy);
            } else if (currentQuantiy > orderItem.getQuantity()) {
                productReposytory.updateProductQuantity(orderItem.getProduct().getProductId(), product.getQuantity() - currentQuantiy);
            } else if (currentQuantiy == orderItem.getQuantity()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie ma potrzeby edytować");
                alert.show();
            }
            orderItemReposytory.updateOrderItemQuantitiById(orderItem.getIdOrderItem(), currentQuantiy);
        }
        productReposytory.closeConnectDB();
        orderItemReposytory.closeConnectDB();
        loadDateOrderItem(order.getIdOrder());
    }

    public void editStatus(MouseEvent mouseEvent) {
            OrderRepository orderRepository=new OrderRepository();
            if(newStatusOrder.getValue()!=null) {
                orderRepository.updateOrderStatus(order.getIdOrder(), newStatusOrder.getValue());
                orderRepository.closeConnectDB();
                statusOrder.setText(newStatusOrder.getValue());
            }else {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Nie wybrałeś statusu zamówienia");
                alert.show();
            }

    }

    public void editUser(MouseEvent mouseEvent) {
        UserRepository userRepository=new UserRepository();
        User user= userRepository.findUserById(Integer.parseInt(idUser.getText()));
        userRepository.closeConnectDB();
        if (user!=null){
            OrderRepository orderRepository=new OrderRepository();
            orderRepository.updateOrderUser(order.getIdOrder(), user);
            orderRepository.closeConnectDB();
        }else {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nie ma użytkownika o takim id ->"+idUser.getText());
            alert.show();
        }
    }
    private void loadDateOrder(){
        idOrder.setText(String.valueOf(order.getIdOrder()));
        idUser.setText(String.valueOf(order.getUser().getId()));
        dateOrder.setText(String.valueOf(order.getDate()));
        statusOrder.setText(order.getStatus());
    }


    public void editOrderDate(MouseEvent mouseEvent) {
        String pattern = "yyyy-MM-dd";
        newDataOrder.setPromptText(pattern.toLowerCase());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        dateOrder.setText(dateFormatter.format(newDataOrder.getValue()));
        LocalDate parsedDate = LocalDate.parse(dateFormatter.format(newDataOrder.getValue()), dateFormatter);

        OrderRepository repository=new OrderRepository();
        repository.updateOrderDate(order.getIdOrder(), parsedDate);
        setOrder(repository.findOrderById(order.getIdOrder()));
        repository.closeConnectDB();
        loadDateOrder();
    }
    public void initializeColumnOrderItem() {
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        product.setCellValueFactory(new PropertyValueFactory<>("product"));
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
}
