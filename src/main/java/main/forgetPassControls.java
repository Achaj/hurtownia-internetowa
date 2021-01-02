package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class forgetPassControls {
   @FXML public TextField login;
    @FXML public PasswordField password;
    @FXML public PasswordField confirmPassword;

    @FXML
    public void confirmDate(ActionEvent actionEvent) {
        System.out.println(password.getText()+"/"+confirmPassword.getText());
        if(password.getText().equals(confirmPassword.getText())){
            Alert info1 = new Alert(Alert.AlertType.CONFIRMATION);
            info1.setTitle("Validacja");
            info1.setHeaderText("Dane Prawidłowe");
            info1.setContentText("kontynuuj");
            info1.show();
        }
    }
}
