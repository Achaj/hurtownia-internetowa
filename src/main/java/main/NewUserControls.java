package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import main.entity.User;
import main.entity.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.regex.Pattern;

public class NewUserControls {
    private static SessionFactory factory;
    public TextField loginEmail;
    public PasswordField passwordUser;
    public TextField firstName;
    public TextField secondName;
    public TextField zipCode;
    public TextField city;
    public TextField street;
    public TextField phoneNumber;
    public Button CreateNewUserButton;
    public ProgressBar progresses ;
    public TextField number;
    public PasswordField passwordConfirmUser;

    private boolean validationStatus=false;
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
    private static final String REGEX_NAME = "[a-zA-Z]{3,20}$";
    private static final String REGEX_ZIPCODE = "\\d{2}(-\\d{3})?";
    private static final String REGEX_STREET_SHORT = "[-a-zA-Z]{3,20}$";
    private static final String REGEX_STREET_MISSING = "(-)";
    private static final String REGEX_STREET_LONG = "^.*?\\s[N]{0,1}([-a-zA-Z0-9]+)\\s*\\w*$";
    private static final String REGEX_PHONE_NUMBER = "[0-9]{6,10}$";
    private static final String REGEX_STREET_NUMBER = "[a-z0-9]{1,5}$";



    public void checkEmail(ActionEvent actionEvent) {
        if(loginEmail.getText()==null){
            System.out.println("Brak adresu email");
            validationStatus=false;
        }else if(loginEmail.getText()!=null){
            validationStatus=true;
            System.out.println(" adresu email");
        }
    }

    public void checkPassword(ActionEvent actionEvent) {

       if(Pattern.matches(REGEX_PASSWORD,passwordUser.getText())){
            validationStatus=true;

        } else{
            System.out.println("Błąd hasła");
            validationStatus=false;
        }
    }

    public void checkUserName(ActionEvent actionEvent) {
        if(Pattern.matches(REGEX_NAME,firstName.getText())){
            validationStatus=true;
            System.out.println("dore imienia");
        }else {
            System.out.println("Bład imienia");
            validationStatus=false;
        }
    }

    public void checkSecondUserName(ActionEvent actionEvent) {
       if(Pattern.matches(REGEX_NAME,secondName.getText())){

           System.out.println("Dobre nazwiska");
            validationStatus=true;

        } else{
            System.out.println("Błąd nazwiska");
            validationStatus=false;
        }
    }

    public void checkZipCode(ActionEvent actionEvent) {

       if(zipCode.getText().matches(REGEX_ZIPCODE)) {

           System.out.println("Dobre zip code");
           validationStatus = true;
       } else {
           System.out.println("Error zip code");
           validationStatus = false;
       }
       System.out.println("The zip code is: " + zipCode.getText());
    }

    public void checkCity(ActionEvent actionEvent) {
        if(Pattern.matches(REGEX_NAME,city.getText())){

            System.out.println("Dobre City");
            validationStatus=true;
        }else {
            System.out.println("ERROR City");
            validationStatus=false;
        }
    }

    public void checkStreet(ActionEvent actionEvent) {
       if(Pattern.matches(REGEX_STREET_SHORT,street.getText())
               ||Pattern.matches(REGEX_STREET_LONG,street.getText())
               ||Pattern.matches(REGEX_STREET_MISSING,street.getText())){

           System.out.println("Dobra street");
            validationStatus=true;
        }
        else{
            System.out.println("Error street");
            validationStatus=false;
        }
    }

    public void checkNumber(ActionEvent actionEvent) {
        if(number.getText().matches(REGEX_STREET_NUMBER)){

            System.out.println("Dobry STREET NUMBER");
            validationStatus=true;

        }
        else{
            System.out.println("ERROR STREET NUMBER");
            validationStatus=false;
        }
    }

    public void checkPhoneNumber(ActionEvent actionEvent) {
        if(phoneNumber.getText().matches(REGEX_PHONE_NUMBER)){

            System.out.println("Dobry PHONE NUMBER");
            validationStatus=true;

        }
        else{
            System.out.println("ERROR PHONE NUMBER");
            validationStatus=false;
        }
    }

    public void checkPasswordConfirm(ActionEvent actionEvent) {
        if(passwordUser.getText().equals(passwordConfirmUser.getText())){

            System.out.println("ok CONFIRM PASS");
            validationStatus=true;
        }else{
            System.out.println("ERROR CONFIRM PASS");
            validationStatus=false;
        }
    }

    public void createNewUser(MouseEvent actionEvent) {

        if(validationStatus==true){
            User user=new User();
            user.setEmail(loginEmail.getText());
            user.setPassword(passwordUser.getText());
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

        }else{
            System.out.println("musisz poprawić coś");


        }
    }

}
