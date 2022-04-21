package com.example.AuthenticationServer.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RegistrationToken")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "Token")
    private String token;

    @Column (name = "Email")
    private String email;

    @Column (name = "ExpirationDate")
    private String expirationDate;

    @Column (name = "CreateBy")
    private String createBy;

}
