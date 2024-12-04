package com.java.eventtracker.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private long id;
    private String username;
    private String email;
    private String password;
    private String roles;
}
