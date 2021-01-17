package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    public void forgetClick(MouseEvent mouseEvent)  throws IOException{
        App.setRoot("forgetPass");
    }

    public void loginDB(ActionEvent actionEvent) throws IOException {
        UserRepository userRepository=new UserRepository();
        User user= userRepository.getUserByEmail(userEmail.getText());
        Alert alert= new Alert(Alert.AlertType.NONE);
        PasswordHashing passwordHashing=new PasswordHashing();
        String hasPass=passwordHashing.doHashing(userPassword.getText());
        if(user!=null){
        if (user.getPassword().equals(hasPass)){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Zalogowano Porawnie");
            alert.setContentText("Dziń dobry "+user.getFirstName());
            TemporayUser temporayUser=new TemporayUser();
            temporayUser.setCurrentUser(user);
            App.setRoot("mainSceneShop");
        }else{

            alert.setAlertType( Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wpisano błędne hasło");
            alert.setContentText("Wpisz poprawne hasło");
        }

        }else{
            alert.setAlertType( Alert.AlertType.WARNING);
            alert.setHeaderText("Nie ma konta z podany adresem email");
        }
        alert.show();
    }
    @FXML
    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("mainSceneShop");
    }
}
