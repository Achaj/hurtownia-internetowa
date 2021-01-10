package main.entity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ProductReposytory {
    EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = entityManagerConnection.getEntityManagerFactory();
    EntityManager entityManager = entityManagerConnection.getEntityManager();
    EntityTransaction entityTransaction = entityManagerConnection.getEntityTransaction();

    public Product saveProduct(Product product) {
        entityTransaction.begin();
        if (product.getProductId() == 0L) {
            try {
                entityManager.persist(product);
            } catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        } else {
            product = entityManager.merge(product);
        }
        entityTransaction.commit();
        return product;
    }

    /*Aby wyszukać produkt podaj jego id*/
    public Product findProductById(long id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    /*Pobranie listy wszystkich Produktów*/
    public List<Product> getAllProduct() {

        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p ", Product.class);
        return productTypedQuery.getResultList();
    }

    /*Pobranie listy wszystkich Produkt "Jednogo " po id*/
    public Product getOneProduct(long id) {

        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p Where p.idProduct=:id", Product.class);
        productTypedQuery.setParameter("id", id);
        return productTypedQuery.getResultList().get(0);
    }

    /*Aby zaktualizowac cenę podaj id produktu następnie nową cene jako String*/
    public Product updateProductPrice(long id, String price) {
        entityTransaction.begin();
        Product product = findProductById(id);
        try {
            product.setPrice(price);
            entityManager.merge(product);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return product;
    }

    /*Aby zaktualizowac ilość sztuk podaj id produktu następnie nową ilość sztuk  jako int*/
    public Product updateProductPrice(long id, int quantity) {
        entityTransaction.begin();
        Product product = findProductById(id);
        try {
            product.setQuantity(quantity);
            entityManager.merge(product);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return product;
    }
    /*Aby zaktualizowac dodatkowe informację podaj id produktu następnie nowe info jako(String)*/
    public Product updateProductInfo(long id, String info) {
        entityTransaction.begin();
        Product product = findProductById(id);
        try {
            product.setInfo(info);
            entityManager.merge(product);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return product;
    }

    /* ABY USUNĄC REKORD Z Product MUSIMY PODAC JEGO ID */
    public void delateOrderItemById(long id) {
        entityTransaction.begin();
        Product product = findProductById(id);
        if (entityManager.contains(product)) {
            entityManager.remove(product);
        } else {
            entityManager.merge(product);
        }
        entityTransaction.commit();
    }
    public void closeConnectDB(){
        entityManagerConnection.closeConnectDB();
    }
}
