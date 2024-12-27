package com.java.eventtracker.users.model;


import com.java.eventtracker.utils.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AuditEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQUENCE")
//    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQUENCE")
    private Long id; // Change from long to Long



    @NotBlank(message = "Username is required and cannot be empty")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Email is required and cannot be empty")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required and cannot be empty")
    private String password;

    private String roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotBlank(message = "Username is required and cannot be empty") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required and cannot be empty") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Email is required and cannot be empty") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required and cannot be empty") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required and cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required and cannot be empty") String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
