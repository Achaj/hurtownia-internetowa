package main.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jan
 */
public class OrderRepository {

    EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = entityManagerConnection.getEntityManagerFactory();
    EntityManager entityManager = entityManagerConnection.getEntityManager();
    EntityTransaction entityTransaction = entityManagerConnection.getEntityTransaction();

    public Order saveOrder(Order order) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        if (order.getIdOrder() == 0) {
            try {
                entityManager.persist(order);
            } catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        } else {
            order = entityManager.merge(order);
        }
        entityTransaction.commit();
        return order;
    }

    public Order findOrderById(int id) {
        Order order = entityManager.find(Order.class, id);
        return order;
    }

    /*Pobranie listy wszystkich zamówien wszystkich urzytkowników*/
    public List<Order> getAllOrder() {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Order> orderTypeQuery = entityManager.createQuery("SELECT o FROM Order o ", Order.class);
        return orderTypeQuery.getResultList();
    }

    /*Pobranie listy wszystkich zamówien urzytkownika*/
    public List<Order> getAllOrderOfOneUser(int id_user) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Order> orderTypeQuery = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.idUser=:id", Order.class);
        orderTypeQuery.setParameter("id", id_user);

        return orderTypeQuery.getResultList();
    }



    /*Aby zaktualizować status zamówienia podajemy id i na jaki status zmieniamy
    data się aktualizuje wraz ze statusem zamówienia
     */
    public Order updateOrderStatus(int id, String status) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        Order order = findOrderById(id);
        try {
            order.setStatus(status);
            order.setDate(new Date());
            entityManager.merge(order);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return order;

    }

    /* ABY USUNĄC REKORD Z ORDER MUSIMY PODAC JEGO ID */
    public void delateOrderItemById(int id) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        Order order = findOrderById(id);
        if (entityManager.contains(order)) {
            entityManager.remove(order);
        } else {
            entityManager.merge(order);
        }
        entityTransaction.commit();
    }
    public void closeConnectDB(){
        entityManagerConnection.closeConnectDB();
    }
}

