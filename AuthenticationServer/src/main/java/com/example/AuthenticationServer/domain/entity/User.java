package com.example.AuthenticationServer.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    // Database table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private boolean activeFlag;

    @Column
    private String createDate;

    @Column
    private String lastModificationDate;

    @Column
    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;


//    // @OneToMany
//    private List<String> permissions;

}
