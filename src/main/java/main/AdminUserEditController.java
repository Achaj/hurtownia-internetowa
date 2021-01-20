package main;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.entity.User;
import main.entity.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminUserEditController implements Initializable {
    public static User user;
    public TextField id;
    public TextField name;
    public TextField secondName;
    public TextField pass;
    public TextField zipCode;
    public TextField city;
    public TextField street;
    public TextField streetNumber;
    public TextField phone;
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
    private static final String REGEX_NAME = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private static final String REGEX_ZIPCODE = "\\d{2}(-\\d{3})?";
    private static final String REGEX_STREET_SHORT = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private static final String REGEX_STREET_MISSING = "(-)";
    private static final String REGEX_STREET_LONG = "^.*?\\s[N]{0,1}([A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]+)\\s*\\w*$";
    private static final String REGEX_PHONE_NUMBER = "[0-9]{6,10}$";
    private static final String REGEX_STREET_NUMBER = "[a-z0-9]{1,5}$";
    public TextField emil;

    public void loadUserData() {
        if (user != null) {
            id.setText(String.valueOf(user.getIdUser()));
            emil.setText(user.getEmail());
            name.setText(user.getFirstName());
            secondName.setText(user.getSecondName());
            zipCode.setText(user.getZipCode());
            city.setText(user.getCity());
            street.setText(user.getStreet());
            streetNumber.setText(user.getNumberInStreet());
            phone.setText(user.getPhoneNumber());
        }
    }

    public void backToMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("AdminUsers");
    }

    public void editPass(MouseEvent mouseEvent) {
        if(user!=null){
        if (checkPassword()) {
            UserRepository userRepository = new UserRepository();
            PasswordHashing passwordHashing = new PasswordHashing();
            userRepository.updatePasswordUserById(user.getIdUser(), passwordHashing.doHashing(pass.getText()));
            userRepository.closeConnectDB();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Twoje hasło jest za słabe");
            alert.show();
        }
        }
    }

    public void editName(MouseEvent mouseEvent) {
        if (user != null) {
            if (Pattern.matches(REGEX_NAME, name.getText()) && name.getText() != null &&
                    Pattern.matches(REGEX_NAME, secondName.getText()) && secondName.getText() != null) {
                UserRepository userRepository = new UserRepository();
                userRepository.updateFirstNameUserById(user.getIdUser(), name.getText(), secondName.getText());
                userRepository.closeConnectDB();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("W imieniu i Nazwisku są dozwolone tylko litery");
                alert.show();
            }
        }
    }

    public void etitAdres(MouseEvent mouseEvent) {
        if(user!=null) {
            if (checkCity() && checkStreet() && checkZipCode() && checkNumberStreet()) {
                UserRepository userRepository = new UserRepository();
                userRepository.updateAddresById(user.getIdUser(), zipCode.getText(), city.getText(), street.getText(), streetNumber.getText());
                userRepository.closeConnectDB();
                loadUserData();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Wprowadzony adres jest nie prawidłowy");
                alert.show();

            }
        }
    }

    public void editEmail(MouseEvent mouseEvent) {
        if (user != null) {
            UserRepository userRepository = new UserRepository();
            userRepository.updateEmailById(user.getIdUser(), emil.getText());
            userRepository.closeConnectDB();

        }
    }

    public boolean checkPassword() {
        if (Pattern.matches(REGEX_PASSWORD, pass.getText()) && pass.getText() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkZipCode() {

        if (zipCode.getText().matches(REGEX_ZIPCODE)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkCity() {
        if (Pattern.matches(REGEX_NAME, city.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkStreet() {
        if (Pattern.matches(REGEX_STREET_SHORT, street.getText())
                || Pattern.matches(REGEX_STREET_LONG, street.getText())
                || Pattern.matches(REGEX_STREET_MISSING, street.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNumberStreet() {
        if (streetNumber.getText().matches(REGEX_STREET_NUMBER)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserData();
    }

    public void editPhone(MouseEvent mouseEvent) {
        if(user!=null){
            if(Pattern.matches(REGEX_PHONE_NUMBER,phone.getText())){
                UserRepository userRepository=new UserRepository();
                userRepository.changeEmail(user.getIdUser(), emil.getText());
                userRepository.closeConnectDB();

            }
        }
    }
}
