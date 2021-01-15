package main.entity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class OrderItemReposytory {
    EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = entityManagerConnection.getEntityManagerFactory();
    EntityManager entityManager = entityManagerConnection.getEntityManager();
    EntityTransaction entityTransaction = entityManagerConnection.getEntityTransaction();

    public OrderItem saveOrderItem(OrderItem orderItem) {
        entityTransaction.begin();
        if (orderItem.getIdOrderItem() == 0L) {
            try {
                ProductReposytory productReposytory = new ProductReposytory();
                Product product = productReposytory.findProductById(orderItem.getProduct().getProductId());
                if (product.getQuantity() - orderItem.getQuantity() > 0) {
                    product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                    entityManager.merge(product);
                    entityManager.persist(orderItem);
                } else {
                    System.out.println("Stan produktu w magazynie produktu w magazynie: " + productReposytory.findProductById(orderItem.getProduct().getProductId()).getQuantity() + "  nie mamy " + orderItem.getQuantity() + " sztuk");
                }
            } catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        } else {
            orderItem = entityManager.merge(orderItem);
        }
        entityTransaction.commit();
        return orderItem;
    }

    /*ABY WYSZUKAC POZYCJĘ ZAMÓWIENIA TRZEBA PODAĆ ID_ORDERITEM*/
    public OrderItem findOrderItemById(int id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        return orderItem;
    }

    /*Pobranie listy wszystkich pozycji zamówien wszystkich urzytkowników*/
    public List<OrderItem> getAllOrderItemsAllOrders() {
        entityTransaction.begin();
        TypedQuery<OrderItem> orderItemTypeQuery = entityManager.createQuery("SELECT i FROM OrderItem i ", OrderItem.class);
        return orderItemTypeQuery.getResultList();
    }

    /*Pobieranie wszystkich pozycji danego zamówienia*/
    public List<OrderItem> getAllOrderItemsOnOneOrder(int id) {
        entityTransaction.begin();
        TypedQuery<OrderItem> orderItemTypeQuery = entityManager.createQuery("SELECT i FROM OrderItem i WHERE i.idOrd erItem=: id", OrderItem.class);
        orderItemTypeQuery.setParameter("id", id);
        return orderItemTypeQuery.getResultList();
    }

    /*ABY ZAKUTALIZOWAĆ LICZBĘ SZTUK W ORDERITEM PODJAEMI ID_ORDERITEM ORAZ LICZBY NA JAKA MA ZAKTUALIZOWAĆ*/
    public OrderItem updateOrderItemQuantitiById(int id, int quantity) {
        entityTransaction.begin();
        ProductReposytory productReposytory = new ProductReposytory();
        OrderItem orderItem = findOrderItemById(id);
        Product product = productReposytory.findProductById(orderItem.getProduct().getProductId());
        //JEŚLI ILOŚC SZTUK JEST WIĘKSZA OD POCZĄTKOWEJ
        try {
            if (quantity > orderItem.getQuantity()) {
                int quantityActual = quantity - orderItem.getQuantity();
                //I NIE PRZEKRACZA ILOŚĆ W ENCJI PRODUCT AKTUALIZACJA SIĘ DOKONA
                if (quantityActual <= product.getQuantity()) {
                    orderItem.setQuantity(quantity);
                    product.setQuantity(product.getQuantity() - quantityActual);
                    orderItem = entityManager.merge(orderItem);
                    product = entityManager.merge(product);
                    entityTransaction.commit();

                } //NIE MOŻNA DOKONAĆ AKTUALIZACJI NIE WYSTARCZAJĄCY ZAPAS W ENCJI PRODUT
                else {
                    System.out.println("Nie mozna zaktualizować zamówionia brak pozycji na magazynie");
                }
            } //JEŚLI ILOŚC SZTUK JEST MNIEJSZA OD POCZĄTKOWEJ
            else if (quantity < orderItem.getQuantity()) {
                int quantityActual = orderItem.getQuantity() - quantity;
                //ILOŚC SZTUK JEST DALEJ WIEKSZA OD ZERA aKTUALIZACJA SIĘ DOKONA
                if (quantityActual > 0) {
                    orderItem.setQuantity(quantity);
                    product.setQuantity(product.getQuantity() + quantityActual);
                    orderItem = entityManager.merge(orderItem);
                    product = entityManager.merge(product);
                    entityTransaction.commit();
                }

            } else if (quantity == orderItem.getQuantity()) {
                System.out.println("Nie ma potrzeby aktualizować bazę na tą sama wartość");
            } //ILOŚĆ SZTUK BESZIE RÓWANA ZERO  PORODUKTY BĘDĄ WZRÓCONE DO ENCJI PRODUCT
            //A REKKORD Z ORDER ITEM ZOSTANN
            else if (quantity == 0) {
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                product = entityManager.merge(product);
                entityTransaction.commit();
                delateOrderItemById(id);
                System.out.println("Rokor zostaje usunięty z bazy");
            }
        } catch (Exception e) {
            entityTransaction.rollback();
        }
        return orderItem;
    }

    public OrderItem updateProductOnOrderId(int idOrderItem, long idProduct) {
        entityTransaction.begin();
        OrderItem orderItem = findOrderItemById(idOrderItem);
        try {
            ProductReposytory productReposytory = new ProductReposytory();
            Product product = productReposytory.findProductById(idProduct);
            orderItem.setProduct(product);
            entityManager.merge(orderItem);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return orderItem;
    }


    /* ABY USUNĄC REKORD Z ORDERITEM MUSIMY PODAC JEGO ID */
    public void delateOrderItemById(int id) {
        entityTransaction.begin();
        OrderItem orderItem = findOrderItemById(id);
        entityManager.remove(orderItem);
        entityTransaction.commit();
    }
    public void closeConnectDB(){
        entityManagerConnection.closeConnectDB();
    }
}

