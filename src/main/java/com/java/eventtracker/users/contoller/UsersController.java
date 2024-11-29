package com.java.eventtracker.users.contoller;


import com.java.eventtracker.constants.UserConstants;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class  UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public Users saveUsers(@Valid @RequestBody Users users)
    {
        return usersService.save(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) throws Exception {
        return usersService.findById(id);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        usersService.deleteById(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.findAll();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody @NonNull Users users) {
        //Call the update method
        usersService.save(users);
        return  ResponseEntity.ok(UserConstants.UPDATE_SUCCESSFUL);
    }

}
