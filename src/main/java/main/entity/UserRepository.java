package main.entity;


import java.util.List;

import main.EntityManagerConnection;

import javax.persistence.*;

public class UserRepository {
/*
    EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = entityManagerConnection.getEntityManagerFactory();
    EntityManager entityManager = entityManagerConnection.getEntityManager();
    EntityTransaction entityTransaction = entityManagerConnection.getEntityTransaction();
*/
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final EntityTransaction entityTransaction = entityManager.getTransaction();

    public User saveUser(User user) {
        entityTransaction.begin();
        try {
            if (user.getId() == 0L) {
                entityManager.persist(user);
            } else {
                user = entityManager.merge(user);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        return user;
    }

    public List<User> getAllUserByName(String name) {
        entityTransaction.begin();
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName=: name", User.class);
        userTypedQuery.setParameter("name", name);
        System.out.println(userTypedQuery);
        return userTypedQuery.getResultList();

    }

    public List<User> getAllUser() {
        entityTransaction.begin();
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
        return userTypedQuery.getResultList();

    }

    public User findUserById(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }
    //aby zmienić imię trzeba podać id w longu i imie na jakie ma być zmienione

    public User updateFirstNameUserById(long id, String newFirsName) {
        entityTransaction.begin();
        User user = findUserById(id);
        try {
            user.setFirstName(newFirsName);
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return user;
    }

    public User updateAddresById(long id, String zipCode, String city, String street, String numberInStreat) {
        entityTransaction.begin();
        User user = findUserById(id);
        try {
            user.setZipCode(zipCode);
            user.setCity(city);
            user.setStreet(street);
            user.setNumberInStreet(numberInStreat);
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return user;
    }

    public User changeAccountTypeToAdmin(long id) {
        entityTransaction.begin();
        User user = findUserById(id);
        try {
            user.setTypeUser("admin");
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return user;
    }

    public void delateUserById(long id) {
        entityTransaction.begin();
        User user = findUserById(id);
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            entityManager.merge(user);
        }
        entityTransaction.commit();
    }

    public void closeConnectDB() {
        entityManager.close();
    }
}

