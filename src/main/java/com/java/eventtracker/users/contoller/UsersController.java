package com.java.eventtracker.users.contoller;


import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
