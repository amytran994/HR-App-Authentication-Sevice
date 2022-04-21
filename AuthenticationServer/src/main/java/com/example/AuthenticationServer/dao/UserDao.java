package com.example.AuthenticationServer.dao;

import com.example.AuthenticationServer.domain.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Optional;

@Repository
public class UserDao {
    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> loadUserByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where username = :username", User.class);
        User user = (User) query.setParameter("username", username).getResultList().get(0);
        return Optional.ofNullable(user);
    }

}
