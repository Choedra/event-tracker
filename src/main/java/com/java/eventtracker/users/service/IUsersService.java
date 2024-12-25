package com.java.eventtracker.users.service;

import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.model.UserDTO;
import com.java.eventtracker.utils.IGenericCrudService;

public interface IUsersService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated users info.
     *
     * @return The users dto
     */
    UserDTO fetchSelfInfo();
}
