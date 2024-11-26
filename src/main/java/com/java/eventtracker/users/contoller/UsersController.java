package com.java.eventtracker.users.contoller;


import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public Users saveUsers(@Validated @RequestBody Users users)
    {
        return usersService.save(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) throws Exception {
        return usersService.findById(id);
    }


}
