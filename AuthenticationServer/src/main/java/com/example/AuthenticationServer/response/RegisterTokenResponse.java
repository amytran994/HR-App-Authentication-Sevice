package com.example.AuthenticationServer.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterTokenResponse {
    private String message;
    private String registerToken;
    private String expiration;
    private String createdBy;
}
