package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.entity.Product;
import main.entity.ProductReposytory;
import main.entity.User;
import main.entity.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminUsersController implements Initializable {
    public TableView<User> tableUsers;
    public TableColumn<User,Integer> idUserColumn;
    public TableColumn<User,String> nameColumn;
    public TableColumn<User,String>  secondNameColumn;
    public TableColumn<User,String>  emailColumn;
    public TableColumn<User,String>  zipcodeColumn;
    public TableColumn<User,String> cityColumn;
    public TableColumn<User,String>  streetColumn;
    public TableColumn<User,String>  streetNumberColumn;
    public TableColumn<User,String>  telephoneColumn;
    public TableColumn<User,String> typeColumn;


    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminMainScene");
    }
    private void initializeColumn(){
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeUser"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
         secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
         emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
         zipcodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
         cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
         streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
         streetNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
         telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("numberInStreet"));
    }
    ObservableList<User> userObservableList;
    private void loadDateUser(){
        tableUsers.getItems().clear();
       UserRepository userRepository=new UserRepository();
       List<User> users=userRepository.getAllUser();
       userRepository.closeConnectDB();
        if(users.size()!=0){
            userObservableList = FXCollections.observableArrayList();
            userObservableList.removeAll(userObservableList);
            userObservableList.addAll(users);
            tableUsers.getItems().addAll(userObservableList);
        }else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak użykowników");
            alert.show();
        }

    }

    public void editUser(MouseEvent mouseEvent) {
    }

    public void addUser(MouseEvent mouseEvent) {
    }

    public void awansAdmin(MouseEvent mouseEvent) {
    }

    public void delateUser(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser();
    }
}
