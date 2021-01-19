package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class bracketSceneControler implements Initializable {
    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product, String> type;
    @FXML
    public TableColumn<Product, String> model;
    @FXML
    public TableColumn<Product, String> producer;
    @FXML
    public TableColumn<Product, String> info;
    @FXML
    public TableColumn<Product, Integer> quantity;
    @FXML
    public TableColumn<Product, String> price;
    @FXML
    public TextField quantityEdit;
    public TextField totalPrice;

    TemporaryBracket bracket = new TemporaryBracket();
    List<Product> products = TemporaryBracket.getProducts();
    ObservableList<Product> productObservableList;

    private int countPrice() {
        int price = 0;
        if (products != null) {
            for (Product product : products) {
                price = (price + product.getQuantity() * product.getPrice());
            }
        }
        return price;
    }

    private void loadDateToTableView() {
        if (products != null) {
            // productObservableList.clear();
            productObservableList = FXCollections.observableArrayList();
            productObservableList.removeAll(products);
            productObservableList.addAll(products);
           /*
           for (Product product : productObservableList) {
               System.out.println(product.toString());
           }*/

            tableView.getItems().addAll(productObservableList);
        } else {

            System.out.println("brak pozycji na liście");
            System.out.println("<--Error-->");
        }
    }

    private void initializeColumn() {
        type.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        model.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        producer.setCellValueFactory(new PropertyValueFactory<Product, String>("producer"));
        info.setCellValueFactory(new PropertyValueFactory<Product, String>("info"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        ;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // System.out.println("<--Temporatr bracket Product-->");
        //   bracket.printsTemporaryBracket();
        setQuantityToOnAllElements();
        initializeColumn();
        loadDateToTableView();
        totalPrice.setText(String.valueOf(countPrice()) + " zł");


    }

    public void addToCurrentBasket(MouseEvent mouseEvent) throws IOException {
        TemporayUser temporayUser = new TemporayUser();
        User user = temporayUser.getCurrentUser();
        //jeśli koszy jest pusty nie zrobisz zamówienia
        if (products == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Brak pozycji w koszyku");
            a.show();
        }
        //jeśli jest coś w koszyku przechodzis dalej
        else {
            //jeśli jesteś nie zalogowany przechodzisz na stronę logowania
            if (user == null) {
                Alert b = new Alert(Alert.AlertType.CONFIRMATION);
                b.setHeaderText("Nie jesteś zalogowany !");
                b.setContentText("Czy chcesz przesjść na stronę logowania ?");
                Optional<ButtonType> result = b.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    App.setRoot("loginScene");
                }else {
                    b.setAlertType(Alert.AlertType.INFORMATION);
                    b.setHeaderText("Nie można kontynuować");
                    b.setContentText("Zaloguj się lub załóż nowe konto");
                }
                b.show();
            }

            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Akceptujesz zamówienie");
                Optional<ButtonType> result = alert.showAndWait();
                //jesli nie zakceptuje zamówienia zamówienie zostanie nie zamówione
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    LocalDate currentDate=LocalDate.now();
                    Order order = new Order();
                    order.setUser(user);
                    order.setDate(currentDate);
                    order.setStatus("Rozpoczęto zamówienie");
                    OrderRepository orderRepository = new OrderRepository();
                    //zapis zamówienia
                    orderRepository.saveOrder(order);
                    order = orderRepository.findOrderById(order.getIdOrder());
                    OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
                    //zapis poszyzj w wzamówieni
                    for (Product p : products) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(order);
                        orderItem.setProduct(p);
                        orderItem.setQuantity(p.getQuantity());
                        orderItemReposytory.saveOrderItem(orderItem);
                    }
                    orderItemReposytory.closeConnectDB();
                    //Po wykonamym zamówienu czysci koszy
                    dellAllCurentBracketMethod();

                } else {
                    System.out.println("anulowano zamówienie");
                }
            }
        }
    }

    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("mainSceneShop");
    }
/*Usuwanie wszystkich produktów z zamówienia */
    public void dellAllCurentBracket(MouseEvent mouseEvent) {
        tableView.getItems().removeAll(tableView.getItems());
        products.clear();
        tableView.refresh();
    }
   /* Usuwanie wszystkich produktów z amówienia*/
    public void dellAllCurentBracketMethod() {
        tableView.getItems().removeAll(tableView.getItems());
        products.clear();
        tableView.refresh();
    }
/*Usuwanie danego obiektu z zamuwienia*/
    public void dellProduct(MouseEvent mouseEvent) {
        Product product = tableView.getSelectionModel().getSelectedItem();
        int index = products.indexOf(product);
        System.out.println("Index-->" + index);
        if (index >= 0) {
            products.remove(index);
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        }

    }
/* Zmiana ilości produktów w zamówienu*/
    public void updateQuantity(MouseEvent mouseEvent) {
        Product product = tableView.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (product == null) {
            a.setHeaderText("Nic nie zaznaczono");
            a.show();
        } else {
            int index = products.indexOf(product);
            if (index >= 0) {
                String editValue = quantityEdit.getText();
                int quntity = Integer.parseInt(editValue);
                product.setQuantity(quntity);
                products.set(index, product);

                System.out.println("<--- befor update --->");
                for (Product p : products) {
                    System.out.println(p.toString());
                }
                totalPrice.setText(String.valueOf(countPrice()) + " zł");
                tableView.refresh();
            } else {
                a.setHeaderText("Brak produkt w koszyku");
                a.show();
            }
        }

    }
/*Ustawianie domyślie ilośc wszystkich prododuktów w zamuwieniu na 1*/
    public void setQuantityToOnAllElements() {
        if (products != null) {
            for (Product p : products) {

                p.setQuantity(1);
            }
        }
        totalPrice.setText(String.valueOf(countPrice()) + " zł");
        tableView.refresh();

    }
}