package main.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

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
    private long idOrder;
    @Column(name = "util_datetime")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItem=new ArrayList<>();


    public Order() {
    }

    public Order(Date date, User user, String status) {
        this.date = date;
        this.user = user;
        this.status = status;
    }


    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
