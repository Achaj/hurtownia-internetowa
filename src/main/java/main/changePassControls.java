package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.entity.User;
import main.entity.UserRepository;

import java.io.IOException;
import java.util.regex.Pattern;

public class changePassControls {
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
   @FXML public TextField oldPass;
    @FXML public PasswordField password;
    @FXML public PasswordField confirmPassword;

    @FXML
    public void confirmDate(ActionEvent actionEvent) throws IOException {
        PasswordHashing passwordHashing=new PasswordHashing();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        //stare hasł musi się zgadzać z hasłem z bazy
        if(user.getPassword().equals(passwordHashing.doHashing(oldPass.getText()))){
            //nowe hasło nie może byc takike samo jak stare
            if(!oldPass.getText().equals(password.getText())|| oldPass.getText().equals(confirmPassword.getText())) {
                //nowe hasło i potwierdzenia hasła muszą być identczne i nowe hasło musi zawierać określoną złożoność
                if (password.getText().equals(confirmPassword.getText()) || Pattern.matches(REGEX_PASSWORD, password.getText())) {
                    String hasPass = passwordHashing.doHashing(password.getText());
                    UserRepository userRepository = new UserRepository();
                    userRepository.updatePasswordUserById(user.getId(), hasPass);
                    userRepository.closeConnectDB();
                    alert.setHeaderText("Hasło zotało zmienione");
                    alert.show();
                    changeSceneLogin();
                }else {
                    alert.setHeaderText("Hasło i Potwierdzenie hasła muszą byc identyczne");
                    alert.show();
                }
            }else {
                alert.setHeaderText("Hasła są identyczne podaj inne");
                alert.show();
            }
        }else {
            alert.setHeaderText("Podaj porawne stare hasło");
            alert.show();

        }

    }
    public void changeSceneLogin() throws IOException {
        App.setRoot("UserSetting");
    }
    TemporayUser temporayUser=new TemporayUser();
    User user= temporayUser.getCurrentUser();

}
