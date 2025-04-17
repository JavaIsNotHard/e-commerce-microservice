package com.example.authorization_server.auth;

import lombok.*;

public class UserRequestDTO {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
