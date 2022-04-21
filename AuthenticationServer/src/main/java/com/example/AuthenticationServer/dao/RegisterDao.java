package com.example.AuthenticationServer.dao;

import com.example.AuthenticationServer.domain.entity.RegistrationToken;
import com.example.AuthenticationServer.domain.entity.Role;
import com.example.AuthenticationServer.domain.entity.User;
import com.example.AuthenticationServer.domain.entity.UserRole;
import com.example.AuthenticationServer.exception.RepeatedFieldException;
import com.example.AuthenticationServer.exception.TokenEmailNotMatchedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public class RegisterDao {

    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public RegistrationToken generateToken (String email, String HRUserName) throws Exception{

        Session session = sessionFactory.getCurrentSession();
        RegistrationToken token = RegistrationToken.builder()
        .token("bcf" + email + "arz")
        .email(email)
        .expirationDate(LocalDate.now().plusDays(1).toString())
        .createBy(HRUserName)
        .build();

        session.save(token);

        return token;
    }

    public User register(String token, String email, String username, String password) throws Exception{

        if (tokenIsNotMatch(token, email)) {
            throw new TokenEmailNotMatchedException("Token and email are not matched");
        }
        if (usernameIsTaken(username)) {
            throw new RepeatedFieldException("This username was taken");
        }
            Session session = sessionFactory.getCurrentSession();
            Role role = session.get(Role.class, 1); //role is employee

            UserRole userrole = UserRole.builder()
            .role(role)
            .activeFlag(true)
            .createDate(LocalDate.now().toString())
            .lastModificationDate(LocalDate.now().toString())
            .build();

            List<UserRole> roles = Arrays.asList(userrole);

            User user = User.builder()
            .email(email)
            .username(username)
            .password(password)
            .roles(roles)
            .activeFlag(true)
            .createDate(LocalDate.now().toString())
            .lastModificationDate(LocalDate.now().toString())
            .build();

            userrole.setUser(user);

            session.save(user);
            session.save(userrole);
            return user;
        
    }

    private boolean tokenIsNotMatch(String token, String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RegistrationToken where token = :tokenName ");
        RegistrationToken tk = (RegistrationToken) query.setParameter("tokenName", token).getResultList().get(0);
        return !token.equals(tk.getToken()) || !email.equals((tk.getEmail()));
    }

    private boolean usernameIsTaken(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where username = :username", User.class);
        int size = query.setParameter("username", username).getResultList().size();
        return size == 1;
    }

}
