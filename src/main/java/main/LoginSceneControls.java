package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.App;
import main.entity.EntityManagerConnection;
import main.entity.User;
import main.entity.UserRepository;

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

    public void loginDB(ActionEvent actionEvent) {
        UserRepository userRepository=new UserRepository();
        User user= userRepository.getUserByEmail(userEmail.getText());
        if (user.getPassword().equals(userPassword.getText())){
            System.out.println("zalogowano poprawnie");
            TemporayUser temporayUser=new TemporayUser();
            temporayUser.setCurrentUser(user);
        }else{
            System.out.println("nie ma takiego email lub błedne hasło");
        }
    }
    @FXML
    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("mainSceneShop");
    }
}
