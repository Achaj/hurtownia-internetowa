package main.entity;


import java.util.List;

import javax.persistence.*;

public class UserRepository {

    EntityManagerConnection entityManagerConnection = new EntityManagerConnection();
    EntityManagerFactory entityManagerFactory = entityManagerConnection.getEntityManagerFactory();
    EntityManager entityManager = entityManagerConnection.getEntityManager();
    EntityTransaction entityTransaction = entityManagerConnection.getEntityTransaction();


    public User saveUser(User user) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (user.getId() == 0) {
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
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName=: name", User.class);
        userTypedQuery.setParameter("name", name);
        System.out.println(userTypedQuery);
        return userTypedQuery.getResultList();

    }
    public User getUserByEmail(String email) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.email=: email", User.class);
        userTypedQuery.setParameter("email", email);
        if(userTypedQuery.getResultList().size()==0){
            return null;
        }else {
            return userTypedQuery.getResultList().get(0);
        }
    }
    public User updatePasswordUserById(int id, String newFirsName) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        try {
            user.setPassword(newFirsName);
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return user;
    }

    public List<User> getAllUser() {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
        return userTypedQuery.getResultList();

    }

    public User findUserById(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }
    //aby zmienić imię trzeba podać id w longu i imie na jakie ma być zmienione

    public boolean updateFirstNameUserById(int id, String newFirsName,String newSecondName) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        boolean status=false;
        try {
            user.setFirstName(newFirsName);
            user.setSecondName(newSecondName);
            entityManager.merge(user);
            entityTransaction.commit();
            status=true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            status=false;
        }
        return status;
    }
    public boolean updateEmailById(int id, String email) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        boolean status=false;
        try {
            user.setEmail(email);
            entityManager.merge(user);
            entityTransaction.commit();
            status=true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            status=false;
        }
        return status;
    }

    public boolean updateAddresById(int id, String zipCode, String city, String street, String numberInStreat) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        boolean status=false;
        try {
            user.setZipCode(zipCode);
            user.setCity(city);
            user.setStreet(street);
            user.setNumberInStreet(numberInStreat);
            entityManager.merge(user);
            entityTransaction.commit();
            status=true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            status=false;
        }
        return status;
    }

    public User changeAccountTypeToAdmin(int id) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
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

    public boolean delateUserById(int id) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        //Pobieramy listę zamówień danego użykonika
        OrderRepository orderRepository=new OrderRepository();
        List<Order> orders=orderRepository.getAllOrderOfOneUser(id);
        User user = findUserById(id);
        boolean status=false;
        if (entityManager.contains(user)) {
            try {
                //jeśli użytkownik posiada zamówienie id użytownika zostaje
                //ustawiona na null a status jeśli nie był zakończony na anulowano
                if(orders!=null) {
                    for (Order order : orders) {
                        if (!order.getStatus().equals("zakończono")) {
                            order.setStatus("Anulowano");
                        }
                        order.setUser(null);
                        entityManager.merge(order);
                    }
                }
                entityManager.remove(user);
               entityTransaction.commit();
                status=true;
            } catch (Exception e) {
                e.printStackTrace();
                entityTransaction.rollback();
                status=false;

            }
        } else {
            entityManager.merge(user);
        }

        return status;
    }

    public void closeConnectDB() {
        entityManagerConnection.closeConnectDB();
    }
}

