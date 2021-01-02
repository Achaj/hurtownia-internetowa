package main.entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserConn");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();


    public User saveUser(User user){
        entityManager.getTransaction().begin();
        if(user.getId()==0L){
            entityManager.persist(user);
        }else{
            user=entityManager.merge(user);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;

    }

}
