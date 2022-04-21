package com.example.AuthenticationServer.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String token;
    private String username;
    private String password;
    private String email;
}
