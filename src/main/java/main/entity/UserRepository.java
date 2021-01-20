package main.entity;


import java.util.List;

import javax.persistence.*;

public class UserRepository {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();


    public User saveUser(User user) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (user.getIdUser() == 0) {
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

    public User changeAccountType(int id,String type) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        try {
            user.setTypeUser(type);
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return user;
    }

    public User changeEmail(int id,String emial) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        User user = findUserById(id);
        try {
            user.setEmail(emial);
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
                //iteruje po lisce zamówień
                if(orders.size()!=0) {
                    for (Order order : orders) {
                        //jeśli zamówienie nie jest anulowane nie zakończone jeszcze

                        if (!order.getStatus().equals("Anulowano")&&!order.getStatus().equals("Zakończono")) {
                            orderRepository.updateOrderStatus(order.getIdOrder(), "Anulowano"); //ustawia status zamówienia na anulowany
                            orderRepository.closeConnectDB(); //zamykania połączenia do tablli zamówenia
                            OrderItemReposytory orderItemReposytory = new OrderItemReposytory();
                            List<OrderItem> orderItems = orderItemReposytory.getAllOrderItemsOnOneOrder(order.getIdOrder()); //pobiera listę zamówienia
                            if (orderItems.size() != 0) {
                                int quntity = 0;
                                ProductReposytory productReposytory = new ProductReposytory();
                                for (OrderItem orderItem : orderItems) {
                                    quntity = orderItem.getQuantity(); //pobiera ilośc produktów z zamówienia
                                    Product productINdB = productReposytory.getOneProduct(orderItem.getProduct().getIdProduct()); //pobiera dany produkt
                                    productReposytory.updateProductQuantity(orderItem.getProduct().getIdProduct(), productINdB.getQuantity() + quntity); //dodaje do magazynu produknu zz anulowanego zamówienia
                                }
                                productReposytory.closeConnectDB();  //zamykanie połączenia do tabeli Produkty
                            }

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
        entityManager.close();
    }


}

