package main;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Color;
import main.entity.User;
import main.entity.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.io.IOException;
import java.util.regex.Pattern;

public class NewUserControls {

    public TextField loginEmail;
    public PasswordField passwordUser;
    public TextField firstName;
    public TextField secondName;
    public TextField zipCode;
    public TextField city;
    public TextField street;
    public TextField phoneNumber;
    public Button CreateNewUserButton;
    public TextField number;
    public PasswordField passwordConfirmUser;
    public Label validated;


    private boolean validationStatus=false;
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
    private static final String REGEX_NAME = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private static final String REGEX_ZIPCODE = "\\d{2}(-\\d{3})?";
    private static final String REGEX_STREET_SHORT = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private static final String REGEX_STREET_MISSING = "(-)";
    private static final String REGEX_STREET_LONG = "^.*?\\s[N]{0,1}([A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]+)\\s*\\w*$";
    private static final String REGEX_PHONE_NUMBER = "[0-9]{6,10}$";
    private static final String REGEX_STREET_NUMBER = "[a-z0-9]{1,5}$";



    public void checkEmail(ActionEvent actionEvent) {
        if(loginEmail.getText().equals("")){
            System.out.println("Brak adresu email");
            validationStatus=false;
            validated.setText("Brak adresu email !");
            validated.setTextFill(Color.RED);
        }else if(loginEmail.getText()!=null){
            validationStatus=true;
            System.out.println(" adresu email");
            validated.setText("Email prawidłowy");

        }
    }

    public void checkPassword(ActionEvent actionEvent) {
       if(Pattern.matches(REGEX_PASSWORD,passwordUser.getText())){
            validationStatus=true;
           validated.setText("Hasło prawidłowe");

        } else{
           validated.setText("Hasło nie prawidłowe !");
           validated.setTextFill(Color.RED);
            System.out.println("Błąd hasła");
            validationStatus=false;
        }
    }

    public void checkUserName(ActionEvent actionEvent) {
        if(Pattern.matches(REGEX_NAME,firstName.getText())){
            validationStatus=true;
            System.out.println("dore imienia");
            validated.setText("Imię prawidłowe");
        }else {
            System.out.println("Bład imienia");
            validationStatus=false;
            validated.setText("Imię nie prawidłowe !");
            validated.setTextFill(Color.RED);
        }
    }

    public void checkSecondUserName(ActionEvent actionEvent) {
       if(Pattern.matches(REGEX_NAME,secondName.getText())){

           System.out.println("Dobre nazwiska");
            validationStatus=true;
           validated.setText("Nazwisko prawidłowe");

        } else{
            System.out.println("Błąd nazwiska");
            validationStatus=false;
           validated.setText("Nazwisko nie prawidłowe");
           validated.setTextFill(Color.RED);
        }
    }

    public void checkZipCode(ActionEvent actionEvent) {

       if(zipCode.getText().matches(REGEX_ZIPCODE)) {

           System.out.println("Dobre zip code");
           validationStatus = true;
           validated.setText("Kod Pocztowy poprawny");
       } else {
           System.out.println("Error zip code");
           validationStatus = false;
           validated.setText("Kod Pocztowy nie poprawny");
           validated.setTextFill(Color.RED);
       }

    }

    public void checkCity(ActionEvent actionEvent) {
        if(Pattern.matches(REGEX_NAME,city.getText())){

            System.out.println("Dobre City");
            validationStatus=true;
            validated.setText("Miasto poprawne");
        }else {
            System.out.println("ERROR City");
            validationStatus=false;
            validated.setText("Miasto nie poprawne");
            validated.setTextFill(Color.RED);
        }
    }

    public void checkStreet(ActionEvent actionEvent) {
       if(Pattern.matches(REGEX_STREET_SHORT,street.getText())
               ||Pattern.matches(REGEX_STREET_LONG,street.getText())
               ||Pattern.matches(REGEX_STREET_MISSING,street.getText())){

           System.out.println("Dobra street");
            validationStatus=true;
           validated.setText("Ulica poprawna");
        }
        else{
            System.out.println("Error street");
            validationStatus=false;
           validated.setText("Ulica nie poprawna");
           validated.setTextFill(Color.RED);
        }
    }

    public void checkNumber(ActionEvent actionEvent) {
        if(number.getText().matches(REGEX_STREET_NUMBER)){

            System.out.println("Dobry STREET NUMBER");
            validationStatus=true;
            validated.setText("Numer ulicy poprawny");

        }
        else{
            System.out.println("ERROR STREET NUMBER");
            validationStatus=false;
            validated.setText("Numer ulicy nie poprawny");
            validated.setTextFill(Color.RED);
        }
    }

    public void checkPhoneNumber(ActionEvent actionEvent) {
        if(phoneNumber.getText().matches(REGEX_PHONE_NUMBER)){

            System.out.println("Dobry PHONE NUMBER");
            validationStatus=true;
            validated.setText("Numer telefonu poprawny");

        }
        else{
            System.out.println("ERROR PHONE NUMBER");
            validationStatus=false;
            validated.setText("Numer telefonu nie poprawny");
            validated.setTextFill(Color.RED);
        }
    }

    public void checkPasswordConfirm(ActionEvent actionEvent) {
        if(passwordUser.getText().equals(passwordConfirmUser.getText())){

            System.out.println("ok CONFIRM PASS");
            validationStatus=true;
            validated.setText("Hasła się zgadzają");
        }else{
            System.out.println("ERROR CONFIRM PASS");
            validationStatus=false;
            validated.setText("Hasła się nie zgadzają");
            validated.setTextFill(Color.RED);
        }
    }

    public void createNewUser(MouseEvent actionEvent) throws IOException {
        PasswordHashing passwordHashing=new PasswordHashing();
        if(validationStatus==true){
            User user=new User();
            user.setEmail(loginEmail.getText());
            user.setPassword(passwordHashing.doHashing(passwordUser.getText()));
            user.setFirstName(firstName.getText());
            user.setSecondName(secondName.getText());
            user.setZipCode(zipCode.getText());
            user.setCity(city.getText());
            user.setStreet(street.getText());
            user.setNumberInStreet(number.getText());
            user.setPhoneNumber(phoneNumber.getText());
            System.out.println(user.toString());
            UserRepository userRepository=new UserRepository();
            userRepository.saveUser(user);
            userRepository.closeConnectDB();
            setSceneAfterCreateAcount();


        }else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Podaj wszystkie dane prawidłowo");
            alert.show();
        }
    }

    public void setSceneAfterCreateAcount() throws IOException {
        TemporayUser temporayUser=new TemporayUser();
        User user=temporayUser.getCurrentUser();
        if(user!=null&&user.getTypeUser().equals("admin")){
            App.setRoot("AdminUsers");
        }else {
            App.setRoot("loginScene");
        }
    }

    public void changeLoginScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("loginScene");
    }
}
