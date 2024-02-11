package com.example.OrderService.model;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private List<String> roles;

    public UserDao(String username, String firstname, String lastname, String password, String email,
            List<String> roles) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
