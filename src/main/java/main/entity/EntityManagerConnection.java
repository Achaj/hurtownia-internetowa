package main.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityManagerConnection {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction entityTransaction = entityManager.getTransaction();
    private  static User user;

    public EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }
    public EntityManager getEntityManager(){
        return entityManager;
    }
    public EntityTransaction getEntityTransaction(){
        return entityTransaction;
    }
    public void closeConnectDB(){
        entityManager.close();
    }

}
