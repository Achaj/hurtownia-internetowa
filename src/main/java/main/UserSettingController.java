package main;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.entity.User;
import main.entity.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSettingController  implements Initializable {
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

    public void backToMainScene(MouseEvent mouseEvent) {
    }

    public void changeNameAndSeconName(MouseEvent mouseEvent) {
        UserRepository  userRepositor = new UserRepository();
        if(userRepositor.updateFirstNameUserById(user.getId(), name.getText(),secondName.getText())==false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Błąd zmiany Imienia i Nazwiska!");
            alert.show();
        }
        userRepositor.closeConnectDB();
        loadUserDate();
    }

    public void changeAdres(MouseEvent mouseEvent) {
        UserRepository  userRepositor = new UserRepository();
        if(userRepositor.updateAddresById(user.getId(),zipCode.getText(),city.getText(),street.getText(),numberInStreet.getText())==false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Błąd zmiany adresu!");
            alert.show();
        }
        userRepositor.closeConnectDB();
        loadUserDate();
    }

    public void deleteAcount(MouseEvent mouseEvent) {
    }

    public void changeEmail(MouseEvent mouseEvent) {
        if (!user.getEmail().equals(newEmail.getText())){

        if(newEmail.getText().equals(newEmailConfirm.getText())) {
            UserRepository userRepositor = new UserRepository();

                if(userRepositor.updateEmailById(user.getId(), newEmail.getText())==false) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Taki adres email jest już zajęty!");
                    alert.show();
                }
                userRepositor.closeConnectDB();

            loadUserDate();
        }else {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Adresy email nie są identyczne !");
            alert.show();
        }
        }else {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Twoje konto jest obecnie ustawione na ten adres !");
            alert.show();
        }
    }
}
