package com.example.AuthenticationServer.service;

import com.example.AuthenticationServer.dao.RegisterDao;
import com.example.AuthenticationServer.domain.entity.RegistrationToken;
import com.example.AuthenticationServer.domain.entity.User;
import com.example.AuthenticationServer.response.RegisterResponse;
import com.example.AuthenticationServer.response.RegisterTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Registration;

@Service
public class RegisterService {

    private RegisterDao registerDao;

    @Autowired
    public void setRegisterDao(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }

    public RegisterTokenResponse generateToken(String email, String HRUserName) throws Exception{
        RegistrationToken token = registerDao.generateToken(email, HRUserName);
        return RegisterTokenResponse.builder().message("Successfully generated registration token")
        .expiration(token.getExpirationDate())
        .createdBy(token.getCreateBy())
        .registerToken(token.getToken()).build();
    }

    public RegisterResponse register(String token, String email, String username, String password) throws Exception{
        User user = registerDao.register(token, email, username, password);
        if (user != null) {
            return RegisterResponse.builder()
                    .message("Successfully register")
                    .username(user.getUsername()).build();
        }
        return RegisterResponse.builder().message("check token and email").build();
    }
}
