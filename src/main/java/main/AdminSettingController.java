package main;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.entity.User;
import main.entity.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminSettingController  implements Initializable {
    public TextField name;
    public TextField secondName;
    public TextField oldEmaiL;
    public TextField newEmail;
    public TextField newEmailConfirm;
    public TextField city;
    public TextField street;
    public TextField numberInStreet;
    public TextField zipCode;
    public Label labelHello;
    TemporayUser temporayUser=new TemporayUser();
    User user=temporayUser.getCurrentUser();


    public void loadUserDate(){
        oldEmaiL.setText(user.getEmail());
        name.setText(user.getFirstName());
        secondName.setText(user.getSecondName());
        city.setText(user.getCity());
        street.setText(user.getStreet());
        numberInStreet.setText(user.getNumberInStreet());
        zipCode.setText(user.getZipCode());
        labelHello.setText("Witaj -> "+user.getFirstName());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadUserDate();
    }

    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminMainScene");
    }

    public void changeNameAndSeconName(MouseEvent mouseEvent) {
        UserRepository userRepositor = new UserRepository();
        Alert alert = new Alert(Alert.AlertType.NONE);
        if(userRepositor.updateFirstNameUserById(user.getId(), name.getText(),secondName.getText())==false) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Błąd zmiany Imienia i nazwiska!");
            alert.show();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie wprowadzono zmiany");
            alert.show();
        }
        userRepositor.closeConnectDB();
        loadUserDate();
    }

    public void changeAdres(MouseEvent mouseEvent) {
        UserRepository  userRepositor = new UserRepository();
        Alert alert = new Alert(Alert.AlertType.NONE);
        if(userRepositor.updateAddresById(user.getId(),zipCode.getText(),city.getText(),street.getText(),numberInStreet.getText())==false) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Błąd zmiany adresu!");
            alert.show();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie wprowadzono zmiany");
            alert.show();
        }
        userRepositor.closeConnectDB();
        loadUserDate();
    }

    public void deleteAcount(MouseEvent mouseEvent) throws IOException {
        UserRepository  userRepositor = new UserRepository();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Czy na pewno chcesz usunąć swoje konto ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK) {
            if(userRepositor.delateUserById(user.getId())==true) {
                temporayUser.setCurrentUser(null);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została wykonana poprawnie");
                alert.setContentText("Zostajesz przemieszcony na stronę głowną sklepu");
                alert.show();
                App.setRoot("mainSceneShop");
            }else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Opracja nie została wykonana poprwnie");
                alert.show();
            }
        }else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Opracja została anulowana");
            alert.show();
        }
        // userRepositor.closeConnectDB();
    }

    public void changeEmail(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (!user.getEmail().equals(newEmail.getText())){

            if(newEmail.getText().equals(newEmailConfirm.getText())) {
                UserRepository userRepositor = new UserRepository();

                if(userRepositor.updateEmailById(user.getId(), newEmail.getText())==false) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Taki adres email jest już zajęty!");
                    alert.show();
                }else {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Pomyślnie wprowadzono zmiany");
                    alert.show();
                }
                userRepositor.closeConnectDB();
                loadUserDate();
            }
            else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Adresy email nie są identyczne !");
                alert.show();
            }
        }else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Twoje konto jest obecnie ustawione na ten adres !");
            alert.show();
        }
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Zostałeś wylogowany");
        alert.show();
        temporayUser.setCurrentUser(null);
        App.setRoot("mainSceneShop");
    }

    public void OrderUserScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("OrderUser");
    }

    public void changePass(MouseEvent mouseEvent) throws IOException {
        App.setRoot("changePass");
    }
}
