package main.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")

public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = true, unique = true)
    private String email;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "firstName", nullable = true)
    private String firstName;
    @Column(name = "secondName", nullable = true)
    private String secondName;
    @Column(name = "zipCode", nullable = true)
    private String zipCode;
    @Column(name = "city", nullable = true)
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "numberInStreet", nullable = true)
    private String numberInStreet;
    @Column(name = "phoneNumber")
    private String phoneNumber;


    public User(String email, String password, String firstName, String secondName, String zipCode, String city, String street, String numberInStreet, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.numberInStreet = numberInStreet;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", numberInStreet='" + numberInStreet + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNumberInStreet(String numberInStreet) {
        this.numberInStreet = numberInStreet;
    }

    public String getNumberInStreet() {
        return numberInStreet;
    }
}
