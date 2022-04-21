package com.example.AuthenticationServer.controller;

import com.example.AuthenticationServer.request.RegisterRequest;
import com.example.AuthenticationServer.request.RegisterTokenRequest;
import com.example.AuthenticationServer.response.RegisterResponse;
import com.example.AuthenticationServer.response.RegisterTokenResponse;
import com.example.AuthenticationServer.security.AuthUserDetail;
import com.example.AuthenticationServer.security.JwtProvider;
import com.example.AuthenticationServer.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class RegisterController {

    private RegisterService registerService;
    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setRegistrationService(RegisterService registrationService) {
        this.registerService = registrationService;
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequest request) throws Exception{
        String token = request.getToken();
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        RegisterResponse res = registerService.register(token, email, username, password);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping("generatetoken")
    public ResponseEntity generatetoken(HttpServletRequest request,
        @RequestBody RegisterTokenRequest requestBody) throws Exception{
        String HRUserName = null;
        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request);
        if (authUserDetailOptional.isPresent()) {
            AuthUserDetail authUserDetail = authUserDetailOptional.get();
            HRUserName = authUserDetail.getUsername();
        }
        RegisterTokenResponse res = registerService.generateToken(requestBody.getEmail(), HRUserName);
        return new ResponseEntity(res, HttpStatus.OK);
    }


}
