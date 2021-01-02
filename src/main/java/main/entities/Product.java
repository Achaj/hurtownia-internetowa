package main.entities;

import javax.persistence.*;
import java.util.Set;
/*
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "producer")
    private String producer;
    @Column(name = "model")
    private String model;
    @Column(name = "info")
    private String info;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private String price;
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Product> products;


    //constructors


    public Product() {
    }

    public Product(String producer, String model, String info, int quantity, String price) {
        this.producer = producer;
        this.model = model;
        this.info = info;
        this.quantity = quantity;
        this.price = price;
    }
    //getters and setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    //to string

    @Override
    public String toString() {
        return "Pordukt{" +
                "id=" + id +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", info='" + info + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                '}';
    }
}*/
