package main.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Table(name = "product", schema = "public")
public class Product implements Serializable {

    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct;
    @Column(name="type")
    private String type;
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
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product() {
    }

    public Product(String type,String producer, String model, String info, int quantity, String price) {
        this.type=type;
        this.producer = producer;
        this.model = model;
        this.info = info;
        this.quantity = quantity;
        this.price = price;

    }

    public long getProductId() {
        return idProduct;
    }

    public void setProductId(long productId) {
        this.idProduct = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", type='" + type + '\'' +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", info='" + info + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}

