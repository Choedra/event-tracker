package com.java.eventtracker.users.service;

import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.model.UserDTO;
import com.java.eventtracker.utils.IGenericCrudService;

public interface UserService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated user info.
     *
     * @return The user dto
     */
    UserDTO fetchSelfInfo();

    /**
     * Updates the user entity.
     *
     * @param user The user entity to be updated.
     * @return The confirmation message on whether the user is updated or not.
     */
    String updateEntity(User user);
}
