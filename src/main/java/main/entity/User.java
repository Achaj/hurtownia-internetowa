package main.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "secondName", nullable = false)
    private String secondName;
    @Column(name = "zipCode", nullable = false,length = 8)
    private String zipCode;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "numberInStreet", nullable = false)
    private String numberInStreet;
    @Column(name = "phoneNumber",nullable = false)
    private String phoneNumber;
    @Column(name = "type_user", nullable = false, columnDefinition = "varchar(255) default 'user'")
    private String typeUser="user";
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> order = new ArrayList<>();

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getNumberInStreet() {
        return numberInStreet;
    }

    public void setNumberInStreet(String numberInStreet) {
        this.numberInStreet = numberInStreet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", numberInStreet='" + numberInStreet + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", typeUser='" + typeUser + '\'' +
                ", order=" + order +
                '}';
    }
}


