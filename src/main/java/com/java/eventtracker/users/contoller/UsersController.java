package com.java.eventtracker.users.contoller;


import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.service.UsersService;
import com.java.eventtracker.utils.RestHelper;
import com.java.eventtracker.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * Signing up the new user.
     *
     * @param users The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody Users users) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", usersService.save(users));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the users by identifier.
     *
     * @param id The unique identifier of the users.
     * @return The user entity.
     */
    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", usersService.findById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        usersService.deleteById(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    /**
     * Fetch self info of the instructor
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", usersService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }
}
