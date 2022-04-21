package com.example.AuthenticationServer.controller;

import com.example.AuthenticationServer.security.AuthUserDetail;
import com.example.AuthenticationServer.security.JwtProvider;
import com.example.AuthenticationServer.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.AuthenticationServer.request.LoginRequest;
import com.example.AuthenticationServer.response.LoginResponse;


@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("auth/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        Authentication authentication;

        try{
          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
          );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();

        String token = jwtProvider.createToken(authUserDetail);

        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();
    }

//    @GetMapping("username")
//    public LoginResponse getUsername(HttpServletRequest request) {
//        String username = "";
//        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request);
//        if (authUserDetailOptional.isPresent()) {
//            AuthUserDetail authUserDetail = authUserDetailOptional.get();
//            username = authUserDetail.getUsername();
//        }
//
//        return LoginResponse.builder()
//                .message("Username: " + username)
//                .build();
//    }


}
