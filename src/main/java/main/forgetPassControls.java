package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.entity.User;
import main.entity.UserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

public class forgetPassControls {
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
   @FXML public TextField login;
    @FXML public PasswordField password;
    @FXML public PasswordField confirmPassword;

    @FXML
    public void confirmDate(ActionEvent actionEvent) throws IOException {
        if(password.getText().equals(confirmPassword.getText())|| Pattern.matches(REGEX_PASSWORD,password.getText())){
                UserRepository userRepository=new UserRepository();
                User user=userRepository.getUserByEmail(login.getText());
                userRepository.updatePasswordUserById(user.getId(), password.getText());
                changeSceneLogin();
        }
    }
    public void changeSceneLogin() throws IOException {
        App.setRoot("loginScene");
    }
}
