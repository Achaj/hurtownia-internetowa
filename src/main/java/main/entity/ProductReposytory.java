package main.entity;

import java.util.List;
import javax.persistence.*;

public class ProductReposytory {
    //EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    public Product saveProduct(Product product) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        if (product.getIdProduct() == 0) {
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
    public Product findProductById(int id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    /*Pobranie listy wszystkich Produktów*/
    public List<Product> getAllProduct() {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p ", Product.class);
        return productTypedQuery.getResultList();
    }
    /*Pobranie listy wszystkich Produktów o wybranym modelu*/
    public List<Product> getAllByProductName(String model) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p Where p.model=:l", Product.class);
        productTypedQuery.setParameter("l", model);
        return productTypedQuery.getResultList();
    }
    /*Pobranie listy wszystkich Produktów o wybranym modelu*/
    public List<Product> getAllByProductNameAndType(String model,String type) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p Where p.model=:l And  p.type=:t", Product.class);
        productTypedQuery.setParameter("l", model);
        productTypedQuery.setParameter("t", type);
        return productTypedQuery.getResultList();
    }

    /*Pobranie listy  Produkt "Jednogo " po id*/
    public Product getOneProduct(int id) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p Where p.idProduct=:id", Product.class);
        productTypedQuery.setParameter("id", id);
        return productTypedQuery.getResultList().get(0);
    }
    /*Pobranie listy  Produktów o określonym typie*/
    public List<Product> getAllProductType(String type) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Product> productTypedQuery = entityManager.createQuery("SELECT p FROM Product p Where p.type=:t", Product.class);
        productTypedQuery.setParameter("t", type);
        return productTypedQuery.getResultList();
    }

    /*Aby zaktualizowac cenę podaj id produktu następnie nową cene jako String*/
    public Product updateProductPrice(int id, int price) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
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
    public Product updateProductQuantity(int id, int quantity) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
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
    public Product updateProductInfo(int id, String info) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
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
    public boolean delateProduct(int id) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        Product product = findProductById(id);
            try {
                entityManager.remove(product);
                entityTransaction.commit();
                return true;
            }catch (Exception  e){
                entityTransaction.rollback();
                return false;
            }
    }
    public void closeConnectDB(){
        entityManager.close();
    }


}
