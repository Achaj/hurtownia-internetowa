package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.App;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class LoginSceneControls{


    @FXML public Label Exit;
    @FXML public TextField userEmail;
    @FXML public PasswordField userPassword;
    @FXML public Button createNewUserButton;
    @FXML public Button userForgetPasswordButton;

    @FXML
    public void handleClose(MouseEvent mouseEvent) {
        System.exit(0);
        System.out.print("exit");
    }
    @FXML
    public void singUpClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("newUserScene");
    }
    @FXML
    public void loginClick(MouseEvent mouseEvent) throws IOException {
            App.setRoot("mainSceneShop");
    }
    @FXML
    public void forgetClick(MouseEvent mouseEvent)  throws IOException{
        App.setRoot("forgetPass");
    }
}
