package main.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Jan
 */
@Entity
@Table(name = "order",schema = "public")
public class Order implements Serializable  {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrder;
    @Column(name = "util_datetime")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "id_user",nullable = true)
    private User user;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItem=new ArrayList<>();


    public Order() {
    }

    public Order(LocalDate date, User user, String status) {
        this.date = date;
        this.user = user;
        this.status = status;
    }


    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

